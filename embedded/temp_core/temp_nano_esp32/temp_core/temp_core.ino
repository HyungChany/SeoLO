#include <sstream>
#include <vector>
#include <string>
#include <Wire.h>
#include <Preferences.h>

#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEServer.h>
#include <Adafruit_PN532.h>
#include <AccelStepper.h>

// String -> Char
std::vector<std::string> splitString(const std::string &s, char delimiter)
{
    std::vector<std::string> tokens;
    std::string token;
    std::istringstream tokenStream(s);
    while (std::getline(tokenStream, token, delimiter))
    {
        tokens.push_back(token);
    }
    return tokens;
}

// BLE 설정
#define SERVICE_UUID "20240520-C104-C104-C104-012345678910"
#define CHARACTERISTIC_UUID "20240521-C104-C104-C104-012345678910"
#define AUTHENTICATION_CODE "SFY001KOR"
#define UID "1DA24G10"
bool isCheckCodeAvailableRunning = false; // 실행 중인지 여부를 추적하는 플래그

// NFC 설정
#define SDA_PIN 4
#define SCL_PIN 5
Adafruit_PN532 nfc(SDA_PIN, SCL_PIN);

// MOTOR 설정
#define IN1 12 // ULN2003의 IN1 핀
#define IN2 11 // ULN2003의 IN2 핀
#define IN3 10 // ULN2003의 IN3 핀
#define IN4 9  // ULN2003의 IN4 핀
AccelStepper stepper(8, IN1, IN3, IN2, IN4);

// LOCK 상태
byte masterKey[4] = {0xE7, 0xE2, 0x02, 0xE7};
byte storedUID[7] = {0};
bool uidStored = false;
bool lockState = false;

// 전원 꺼져도 플래시메모리 남아있도록 설정
Preferences preferences;

// BATTERY 설정
const int analogPin = A3;           // 아두이노 나노 ESP32의 A3 아날로그 입력 핀 (GPIO 15)
const float voltageDivider = 2.0;   // 전압 분배 계수 (10K옴 + 10K옴 = 입력 전압의 반)
const float referenceVoltage = 3.3; // ESP32의 기준 전압
const int resolution = 4095;        // ESP32의 ADC 해상도 (0-4095)

String savedToken = "";
String savedMachine = "";
BLECharacteristic *pCharacteristic;
BLEServer *pServer = NULL;
BLEService *messageService = NULL;
BLECharacteristic *stringCharacteristic = NULL;

String receivedString = "";
String companyCode = "";
String code = "";
String token = "";
String machine = "";
String user = "";
int battery = 0;

class MyCallbacks : public BLECharacteristicCallbacks
{
public:
    void onWrite(BLECharacteristic *characteristic)
    {
        std::string receivedString = characteristic->getValue();

        // 데이터가 없을 경우 예외처리
        if (receivedString.empty())
        {
            stringCharacteristic->setValue(",,,,");
            Serial.println("Empty data received");
            return;
        }

        // 쉼표로 문자열을 분할
        std::vector<std::string> tokens = splitString(receivedString, ',');

        // 토큰이 5개 미만인 경우 예외처리
        if (tokens.size() < 5)
        {
            stringCharacteristic->setValue(",,,,");
            Serial.println("Insufficient data received");
            return;
        }

        // 토큰들을 순서대로 할당
        companyCode = tokens[0].c_str();
        code = tokens[1].c_str();
        token = tokens[2].c_str();
        machine = tokens[3].c_str();
        user = tokens[4].c_str();

        Serial.print("BLE MESSAGE FROM CLIENT : ");
        Serial.print(companyCode.c_str());
        Serial.print(", ");
        Serial.print(code.c_str());
        Serial.print(", ");
        Serial.print(token.c_str());
        Serial.print(", ");
        Serial.print(machine.c_str());
        Serial.print(", ");
        Serial.println(user.c_str());

        // 회사 코드가 쓰인 시점에 메세지를 쓴! 클라이언트의 회사 코드를 확인하고, 다른 경우 연결을 해제합니다.
        if (pServer->getConnectedCount() > 0)
        {
            std::map<uint16_t, conn_status_t> peerDevices = pServer->getPeerDevices(false);
            for (auto conn : peerDevices)
            {
                uint16_t connId = conn.first;
                conn_status_t connStatus = conn.second;
                if (connStatus.connected && connStatus.peer_device != nullptr)
                {
                    // 클라이언트의 회사 코드와 쓰여진 회사 코드를 비교
                    if (companyCode != AUTHENTICATION_CODE)
                    {
                        // 회사 코드가 다를 경우 클라이언트의 연결을 해제
                        pServer->disconnect(connId);
                    }
                    else
                    {
                        checkCodeAvailable(code, token, machine, user);
                    }
                }
            }
        }
    }
};

class MyServerCallbacks : public BLEServerCallbacks
{
public:
    void onConnect(BLEServer *pServer)
    {
        int connectedCount = pServer->getConnectedCount() + 1;
        Serial.print("Connected devices count: ");
        Serial.println(connectedCount);
        pServer->startAdvertising();
    }

    void onDisconnect(BLEServer *pServer)
    {
        int connectedCount = pServer->getConnectedCount() - 1;
        Serial.print("Connected devices count: ");
        Serial.println(connectedCount);
        pServer->startAdvertising();
    }
};

MyServerCallbacks serverCallbacks;

void checkCodeAvailable(String code, String token, String machine, String user)
{
    String message = "";

    if (isCheckCodeAvailableRunning)
    {
        Serial.println("checkCodeAvailable is already running.");
        return; // 다른 checkCodeAvailable 함수가 실행 중이면 함수를 종료합니다.
    }
    isCheckCodeAvailableRunning = true; // 함수가 실행 중임을 표시합니다.

    if (code == "INIT")
    {
        if (savedToken != "")
        {
            // "CHECK, UID, MachineId, BATTERY" 전송
            message += "CHECK";
            message += ",";
            message += UID;
            message += ",";
            message += savedMachine;
        }
        else
        {
            // "WRITE, UID, BATTERY" 전송
            message += "WRITE";
            message += ",";
            message += UID;
            message += ",";
        }
    }
    else if (code == "LOCKED")
    {
        if (savedToken == "")
        {
            // "ALERT, BATTERY" 전송
            message += "ALERT";
            message += ",";
            message += ",";
        }
        else if (savedToken == token)
        {
            // "UNLOCK, UID, BATTERY" 전송
            message += "UNLOCK";
            message += ",";
            message += UID;
            message += ",";

            // 자물쇠 열기
            stepper.moveTo(-700);

            // 내장된 정보 삭제
            savedToken = "";
            savedMachine = "";
        }
        else
        {
            // "CHECK, UID, machineId, BATTERY" 전송
            message += "CHECK";
            message += ",";
            message += UID;
            message += ",";
            message += savedMachine;
        }
    }
    else if (code == "LOCK")
    {
        if (token != "" && savedToken == "")
        {
            // 자물쇠에 정보 저장
            savedMachine = machine;
            savedToken = token;
            // 자물쇠 잠그기
            stepper.moveTo(700);

            // 잠금되면 데이터 전송("LOCKED",  "UID", "BATTERY")
            message += "LOCKED";
            message += ",";
            message += UID;
            message += ",";
        }
        else if (token == savedToken)
        {
            // 자물쇠 잠금
            stepper.moveTo(700);

            // 잠금되면 데이터 전송("LOCKED", "UID", "BATTERY")
            message += "LOCKED";
            message += ",";
            message += UID;
            message += ",";
            message += savedMachine;
        }
        else
        {
            message += ",";
            message += ",";
        }
    }
    else
    {
        message += ",";
        message += ",";
    }

    int adcValue = analogRead(analogPin);
    float batteryVoltage = (adcValue * referenceVoltage / resolution) * voltageDivider;
    int battery = mapBatteryVoltageToPercentage(batteryVoltage);

    message += ",";
    message += battery;
    message += ",";
    message += user;

    // message 전송
    Serial.print("BLE SENT MESSAGE : ");
    Serial.println(message);
    Serial.print("savedToken : ");
    Serial.println(savedToken);
    Serial.print("savedMachine : ");
    Serial.println(savedMachine);
    stringCharacteristic->setValue(message.c_str());

    isCheckCodeAvailableRunning = false; // 함수가 실행을 마쳤음을 표시합니다.
}

void setup()
{
    Serial.begin(115200);
    while (!Serial)
        ;

    pinMode(IN1, OUTPUT);
    pinMode(IN2, OUTPUT);
    pinMode(IN3, OUTPUT);
    pinMode(IN4, OUTPUT);

    stepper.setMaxSpeed(500);
    stepper.setAcceleration(200);

    nfc.begin();
    uint32_t versiondata = nfc.getFirmwareVersion();
    if (!versiondata)
    {
        Serial.print("PN53x 보드를 찾을 수 없습니다");
        while (1)
            ;
    }

    nfc.SAMConfig();
    Serial.println("NFC 카드를 기다리는 중...");

    BLEDevice::init("SEOLO LOCK 1");

    pServer = BLEDevice::createServer();
    BLEService *pService = pServer->createService(BLEUUID(SERVICE_UUID));

    pCharacteristic = pService->createCharacteristic(
        BLEUUID(CHARACTERISTIC_UUID),
        BLECharacteristic::PROPERTY_READ |
            BLECharacteristic::PROPERTY_WRITE |
            BLECharacteristic::PROPERTY_NOTIFY);

    pCharacteristic->setCallbacks(new MyCallbacks());

    pService->start();

    BLEAdvertising *pAdvertising = pServer->getAdvertising();
    pAdvertising->start();

    Serial.println("SEOLO LOCK 1");

    // stringCharacteristic, battery 초기화
    stringCharacteristic = pCharacteristic;
    battery = 0;

    // 연결 및 연결 해제 이벤트 핸들러 등록
    pServer->setCallbacks(&serverCallbacks);

    preferences.begin("nfc-data", false);
    uidStored = preferences.getBool("uidStored", false);
    lockState = preferences.getBool("lockState", false);
    savedToken = preferences.getString("savedToken", "");
    savedMachine = preferences.getString("savedMachine", "");
    if (uidStored)
    {
        size_t len = preferences.getBytesLength("storedUID");
        if (len == sizeof(storedUID))
        {
            preferences.getBytes("storedUID", storedUID, len);
        }
    }
}

void loop()
{
    // 배터리 상태 확인
    int adcValue = analogRead(analogPin);
    float batteryVoltage = (adcValue * referenceVoltage / resolution) * voltageDivider;
    battery = mapBatteryVoltageToPercentage(batteryVoltage);

    // 디지털 핀의 상태를 읽어 종료 요청을 확인합니다.
    // 만약 종료 요청이 들어오면 저장 로직 실행 후 재부팅합니다.
    // if (digitalRead(EXIT_PIN) == HIGH || battery <= 10) {
    //     // 종료 요청 시 저장 로직 실행
    //     preferences.putString("savedToken", savedToken);
    //     preferences.putString("savedMachine", savedMachine);
    //     preferences.putBool("uidStored", uidStored);
    //     preferences.putBool("lockState", lockState);
    //     preferences.putBytes("storedUID", storedUID, sizeof(storedUID));
    //     preferences.end(); // Preferences 객체 종료
    //     delay(100); // 종료 지연
    //     ESP.restart(); // 재부팅
    // }

    readNFCAndBattery(); // NFC와 배터리 상태를 확인합니다.
    while (stepper.distanceToGo() != 0)
    {
        stepper.run();
    }
    disableMotor(); // 모터 사용 완료 후 비활성화

    delay(5000); // 5초 딜레이
}

void disableMotor()
{
    // 모터 핀을 LOW로 설정하여 모터를 비활성화
    digitalWrite(IN1, LOW);
    digitalWrite(IN2, LOW);
    digitalWrite(IN3, LOW);
    digitalWrite(IN4, LOW);
}

void readNFCAndBattery()
{
    uint8_t success;
    uint8_t uid[7];
    uint8_t uidLength;

    success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);

    // 배터리 상태 측정
    int adcValue = analogRead(analogPin);
    float batteryVoltage = (adcValue * referenceVoltage / resolution) * voltageDivider;
    int battery = mapBatteryVoltageToPercentage(batteryVoltage);
    Serial.print("배터리: ");
    Serial.print(batteryVoltage);
    Serial.print(" V - ");
    Serial.print(battery);
    Serial.println("%");

    if (success)
    {
        Serial.print("UID 길이: ");
        Serial.print(uidLength, DEC);
        Serial.print(" 바이트 /");
        Serial.print(" UID 값:");
        for (uint8_t i = 0; i < uidLength; i++)
        {
            Serial.print(" 0x");
            Serial.print(uid[i], HEX);
        }
        Serial.println("");

        handleNFC(uid, uidLength);
    }
}

// NFC에 따른 로직
void handleNFC(uint8_t *uid, uint8_t uidLength)
{
    if (lockState)
    {
        if (memcmp(masterKey, uid, 4) == 0)
        {
            Serial.println("마스터 키/ 초기화. 잠금 해제.");
            clearStoredUID();
            savedToken = "";
            savedMachine = "";
            stepper.moveTo(-700);
        }
        else
        {
            Serial.println("불일치.");
        }
    }
    else
    {
        if (!uidStored)
        {
            memcpy(storedUID, uid, uidLength);
            uidStored = true;
            preferences.putBool("uidStored", uidStored);
            preferences.putBytes("storedUID", storedUID, uidLength);
            lockState = true;
            preferences.putBool("lockState", lockState);
            Serial.println("저장. 잠금 상태 활성화.");
            stepper.moveTo(700);
        }
    }
}

// BATTERY 계산
int mapBatteryVoltageToPercentage(float voltage)
{
    if (voltage >= 4.09)
        return 100;
    if (voltage >= 4.0)
        return 95;
    if (voltage >= 3.9)
        return 90;
    if (voltage >= 3.8)
        return 85;
    if (voltage >= 3.7)
        return 75;
    if (voltage >= 3.6)
        return 65;
    if (voltage >= 3.5)
        return 55;
    if (voltage >= 3.4)
        return 45;
    if (voltage >= 3.3)
        return 35;
    if (voltage >= 3.2)
        return 25;
    if (voltage >= 3.1)
        return 15;
    if (voltage >= 3.0)
        return 5;
    return 0;
}

// UID 삭제
void clearStoredUID()
{
    memset(storedUID, 0, sizeof(storedUID));
    uidStored = false;
    lockState = false;
    preferences.putBool("uidStored", uidStored);
    preferences.putBool("lockState", lockState);
    preferences.remove("storedUID");
}
