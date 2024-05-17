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

#include <Arduino.h>
#include <AESLib.h>
#include "base64.h"
#include "libb64/cdecode.h"

// String -> Char
std::vector<std::string> splitString(const std::string &s, char delimiter) {
  std::vector<std::string> tokens;
  std::string token;
  std::istringstream tokenStream(s);
  while (std::getline(tokenStream, token, delimiter)) {
    tokens.push_back(token);
  }
  return tokens;
}

// BLE 설정
#define SERVICE_UUID "20240520-C104-C104-C104-012345678910"
#define CHARACTERISTIC_UUID "20240521-C104-C104-C104-012345678910"
#define AUTHENTICATION_CODE "SFY001KOR"
#define UID "1DA24G10"
#define AES_KEY "1Uxl86dVL5irFevWjwPhRg=="

bool isCheckCodeAvailableRunning = false;  // 실행 중인지 여부를 추적하는 플래그

// NFC 설정
#define SDA_PIN 4
#define SCL_PIN 5
Adafruit_PN532 nfc(SDA_PIN, SCL_PIN);

// MOTOR 설정
#define IN1 12  // ULN2003의 IN1 핀
#define IN2 11  // ULN2003의 IN2 핀
#define IN3 10  // ULN2003의 IN3 핀
#define IN4 9   // ULN2003의 IN4 핀
AccelStepper stepper(8, IN1, IN3, IN2, IN4);

// LOCK 상태
const byte masterKey[4] = { 0xA3, 0x84, 0x1E, 0x27 };
byte storedUID[7] = { 0 };
bool uidStored = false;
bool lockState = false;

// 전원 꺼져도 플래시메모리 남아있도록 설정
Preferences preferences;

// BATTERY 설정
const int analogPin = A3;            // 아두이노 나노 ESP32의 A3 아날로그 입력 핀 (GPIO 15)
const float voltageDivider = 2.0;    // 전압 분배 계수 (10K옴 + 10K옴 = 입력 전압의 반)
const float referenceVoltage = 3.3;  // ESP32의 기준 전압
const int resolution = 4095;         // ESP32의 ADC 해상도 (0-4095)

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
String feUID = "";
int battery = 0;


void disableMotor() {
  // 모터 핀을 LOW로 설정하여 모터를 비활성화
  digitalWrite(IN1, LOW);
  digitalWrite(IN2, LOW);
  digitalWrite(IN3, LOW);
  digitalWrite(IN4, LOW);
}

void readNFCAndBattery() {
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

  if (success) {
    Serial.print("UID 길이: ");
    Serial.print(uidLength, DEC);
    Serial.print(" 바이트 /");
    Serial.print(" UID 값:");
    for (uint8_t i = 0; i < uidLength; i++) {
      Serial.print(" 0x");
      Serial.print(uid[i], HEX);
    }
    Serial.println("");

    handleNFC(uid, uidLength);
  }
}

// UID 삭제
void clearStoredUID() {
  memset(storedUID, 0, sizeof(storedUID));
  uidStored = false;
  lockState = false;
  preferences.putBool("uidStored", uidStored);
  preferences.putBool("lockState", lockState);
  preferences.remove("storedUID");
}

// NFC에 따른 로직
void handleNFC(uint8_t *uid, uint8_t uidLength) {
  if (lockState) {
    if (memcmp(masterKey, uid, 4) == 0) {
      Serial.println("마스터 키/ 초기화. 잠금 해제.");
      clearStoredUID();
      savedToken = "";
      savedMachine = "";
      stepper.moveTo(-700);
    } else {
      Serial.println("불일치.");
    }
  } else {
    if (!uidStored) {
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
int mapBatteryVoltageToPercentage(float voltage) {
  if (voltage >= 4.09)
    return 100;
  if (voltage >= 4.0)
    return 95;
  if (voltage >= 3.9)
    return 90;
  if (voltage >= 3.8)
    return 85;
  if (voltage >= 3.75)
    return 80;
  if (voltage >= 3.7)
    return 75;
  if (voltage >= 3.65)
    return 70;
  if (voltage >= 3.6)
    return 65;
  if (voltage >= 3.55)
    return 60;
  if (voltage >= 3.5)
    return 55;
  if (voltage >= 3.45)
    return 50;
  if (voltage >= 3.4)
    return 45;
  if (voltage >= 3.35)
    return 40;
  if (voltage >= 3.3)
    return 35;
  if (voltage >= 3.25)
    return 30;
  if (voltage >= 3.2)
    return 25;
  if (voltage >= 3.15)
    return 20;
  if (voltage >= 3.1)
    return 15;
  if (voltage >= 3.05)
    return 10;
  if (voltage >= 3.0)
    return 5;
  return 0;
}

class MyCallbacks : public BLECharacteristicCallbacks {
public:
  void onWrite(BLECharacteristic *characteristic) {
    std::string receivedString = characteristic->getValue();

    // 데이터가 없을 경우 예외처리
    if (receivedString.empty()) {
      stringCharacteristic->setValue(",,,,");
      Serial.println("Empty data received");
      return;
    }

    // 쉼표로 문자열을 분할
    std::vector<std::string> tokens = splitString(receivedString, ',');

    // 토큰이 6개 미만인 경우 예외처리
    if (tokens.size() < 6) {
      stringCharacteristic->setValue(",,,,");
      Serial.println("Insufficient data received");
      return;
    }

    // 토큰들을 순서대로 할당
    companyCode = tokens[0].c_str();
    token = tokens[1].c_str();
    machine = tokens[2].c_str();
    user = tokens[3].c_str();
    feUID = tokens[4].c_str();
    code = tokens[5].c_str();

    Serial.print("BLE MESSAGE FROM CLIENT : ");
    Serial.print(companyCode.c_str());
    Serial.print(",");
    Serial.print(token.c_str());
    Serial.print(",");
    Serial.print(machine.c_str());
    Serial.print(",");
    Serial.print(user.c_str());
    Serial.print(",");
    Serial.print(feUID.c_str());
    Serial.print(",");
    Serial.println(code.c_str());

    // 회사 코드가 쓰인 시점에 메세지를 쓴! 클라이언트의 회사 코드를 확인하고, 다른 경우 연결을 해제합니다.
    if (pServer->getConnectedCount() > 0) {
      std::map<uint16_t, conn_status_t> peerDevices = pServer->getPeerDevices(false);
      for (auto conn : peerDevices) {
        uint16_t connId = conn.first;
        conn_status_t connStatus = conn.second;
        if (connStatus.connected && connStatus.peer_device != nullptr) {
          // 클라이언트의 회사 코드와 쓰여진 회사 코드를 비교, user유무 확인
          if (companyCode != AUTHENTICATION_CODE || user == "") {
            // 회사 코드가 다르거나 user를 보내지 않은 경우 클라이언트의 연결을 해제
            pServer->disconnect(connId);
          } else {
            checkCodeAvailable(code, token, machine, user, feUID);
          }
        }
      }
    }
  }
};

class MyServerCallbacks : public BLEServerCallbacks {
public:
  void onConnect(BLEServer *pServer) {
    int connectedCount = pServer->getConnectedCount() + 1;
    Serial.print("Connected devices count: ");
    Serial.println(connectedCount);
    pServer->startAdvertising();
  }

  void onDisconnect(BLEServer *pServer) {
    int connectedCount = pServer->getConnectedCount() - 1;
    Serial.print("Connected devices count: ");
    Serial.println(connectedCount);
    pServer->startAdvertising();

    stepper.moveTo(700);
  }
};

MyServerCallbacks serverCallbacks;

// Helper function to decode Base64
std::string base64Decode(const std::string& toBeDecoded) {
  // Allocate a buffer large enough to hold the decoded data
  int bufferLength = (toBeDecoded.length() * 3) / 4;
  std::string decoded;
  decoded.resize(bufferLength);

  base64_decodestate s;
  base64_init_decodestate(&s);

  // Perform the decoding
  int decodedLength = base64_decode_block(toBeDecoded.c_str(), toBeDecoded.length(), &decoded[0], &s);
  decoded.resize(decodedLength);  // Resize to actual decoded length

  return decoded;
}

// std::string base64Decode(std::string toBeDecoded) {
//   char decoded[64];
//   base64_decodestate s;
//   base64_init_decodestate(&s);

//   int decodedLength = base64_decode_block(toBeDecoded.c_str(), toBeDecoded.length(), decoded, &s);
//   decoded[decodedLength] = '\0';

//   return decoded;
// }

// Helper function to decrypt AES-128 ECB
std::string decryptAES128ECB(const std::string &ciphertext, const std::string &base64Key) {
  // Base64 디코딩된 텍스트를 얻기
  std::string decodedCiphertext = base64Decode(ciphertext);

  Serial.print("decodedCiphertext: ");
  for (size_t i = 0; i < decodedCiphertext.size(); ++i) {
    Serial.print(decodedCiphertext[i], HEX);
    Serial.print(" ");
  }
  Serial.println();

  // Base64 디코딩된 키를 얻기
  std::string key = base64Decode(base64Key);
  
  Serial.print("key ");
  for (size_t i = 0; i < key.size(); ++i) {
    Serial.print(key[i], HEX);
    Serial.print(" ");
  }
  Serial.println();

  // AES 객체 생성
  AES aes;
  byte keyBytes[16];
  byte plaintextBytes[16];

  // 키를 바이트 배열로 변환
  memcpy(keyBytes, key.data(), 16);

  // AES 키 설정
  aes.set_key(keyBytes, sizeof(keyBytes));

  // 복호화 수행
  aes.decrypt(reinterpret_cast<const byte *>(decodedCiphertext.data()), plaintextBytes);

  Serial.print("decrypted: ");
  for (size_t i = 0; i < 16; ++i) {
    Serial.print(plaintextBytes[i], HEX);
    Serial.print(" ");
  }
  Serial.println();

  // 복호화된 결과를 문자열로 반환
  return std::string(reinterpret_cast<char *>(plaintextBytes), 16);
}

void checkCodeAvailable(String code, String token, String machine, String user, String feUID) {
  String message = "";

  if (isCheckCodeAvailableRunning) {
    Serial.println("checkCodeAvailable is already running.");
    return;  // 다른 checkCodeAvailable 함수가 실행 중이면 함수를 종료합니다.
  }
  isCheckCodeAvailableRunning = true;  // 함수가 실행 중임을 표시합니다.

  std::string decodedToken = base64Decode(token.c_str());
  std::string decodedKey = base64Decode(AES_KEY);

  std::string decryptedToken = decryptAES128ECB(decodedToken, decodedKey);

  String decryptedTokenString = String(decryptedToken.c_str());

  if (code == "INIT") {
    if (savedToken != "") {
      // "CHECK, UID, MachineId, BATTERY" 전송
      message += "CHECK";
      message += ",";
      message += UID;
      message += ",";
      message += savedMachine;
    } else if (machine == "") {
      // "WRITE, UID, BATTERY" 전송
      message += "WRITE";
      message += ",";
      message += UID;
      message += ",";
      message += machine;
    } else if (machine != "") {
      // "WRITED, UID, BATTERY" 전송
      message += "WRITED";
      message += ",";
      message += UID;
      message += ",";
      message += machine;
      savedMachine = machine;
    }
  } else if (code == "LOCKED") {
    if (savedToken == "") {
      // "ALERT, BATTERY" 전송
      message += "ALERT";
      message += ",";
      message += UID;
      message += ",";
      message += savedMachine;

    } else if (savedToken == decryptedTokenString) {
      // "UNLOCK, UID, BATTERY" 전송
      message += "UNLOCK";
      message += ",";
      message += UID;
      message += ",";
      message += savedMachine;

      // 자물쇠 열기
      stepper.moveTo(-700);

      // 내장된 정보 삭제
      savedToken = "";
      savedMachine = "";
    } else {                         
      // "CHECK, UID, machineId, BATTERY" 전송
      message += "CHECK";
      message += ",";
      message += UID;
      message += ",";
      message += savedMachine;
    }
  } else if (code == "LOCK") {
    if (token != "" && savedToken == "" && feUID == UID) {
      // 자물쇠에 정보 저장
      savedMachine = machine;
      savedToken = decryptedTokenString;
      // 자물쇠 잠그기
      stepper.moveTo(700);

      // 잠금되면 데이터 전송("LOCKED",  "UID", "BATTERY")
      message += "LOCKED";
      message += ",";
      message += UID;
      message += ",";
      message += savedMachine;
    } else if (decryptedTokenString == savedToken && feUID == UID) {
      // 자물쇠 잠금
      stepper.moveTo(700);

      // 잠금되면 데이터 전송("LOCKED", "UID", "BATTERY")
      message += "LOCKED";
      message += ",";
      message += UID;
      message += ",";
      message += savedMachine;
    } else {
      message += ",";
      message += ",";
    }
  } else {
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
  Serial.println();

  stringCharacteristic->setValue(message.c_str());
  stringCharacteristic->notify();

  isCheckCodeAvailableRunning = false;  // 함수가 실행을 마쳤음을 표시합니다.

  while (stepper.distanceToGo() != 0) {
    stepper.run();
  }
  disableMotor();  // 모터 사용 완료 후 비활성화
}

void setup(void) {
  Serial.begin(115200);
  while (!Serial);

  pinMode(IN1, OUTPUT);
  pinMode(IN2, OUTPUT);
  pinMode(IN3, OUTPUT);
  pinMode(IN4, OUTPUT);

  stepper.setMaxSpeed(2000);
  stepper.setAcceleration(1000);

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
    BLECharacteristic::PROPERTY_READ | BLECharacteristic::PROPERTY_WRITE | BLECharacteristic::PROPERTY_NOTIFY);

  pCharacteristic->setCallbacks(new MyCallbacks());

  pService->start();

  BLEAdvertising *pAdvertising = BLEDevice::getAdvertising();
  pAdvertising->addServiceUUID(pService->getUUID());
  pAdvertising->setScanResponse(false);
  pAdvertising->start();

  Serial.println("SEOLO LOCK 1");

  // stringCharacteristic, battery 초기화
  stringCharacteristic = pService->getCharacteristic(BLEUUID(CHARACTERISTIC_UUID));
  battery = 0;

  // 연결 및 연결 해제 이벤트 핸들러 등록
  pServer->setCallbacks(&serverCallbacks);

  preferences.begin("nfc-data", false);
  uidStored = preferences.getBool("uidStored", false);
  lockState = preferences.getBool("lockState", false);
  savedToken = preferences.getString("savedToken", "");
  savedMachine = preferences.getString("savedMachine", "");
  if (uidStored) {
    size_t len = preferences.getBytesLength("storedUID");
    if (len == sizeof(storedUID)) {
      preferences.getBytes("storedUID", storedUID, len);
    }
  }
}

void loop(void) {
  // BLEDevice::loop(); //  BLE이벤트 처리
  readNFCAndBattery();  // NFC와 배터리 상태를 확인합니다.
  while (stepper.distanceToGo() != 0) {
    stepper.run();
  }
  disableMotor();  // 모터 사용 완료 후 비활성화

  // 배터리 상태 확인
  int adcValue = analogRead(analogPin);
  float batteryVoltage = (adcValue * referenceVoltage / resolution) * voltageDivider;
  battery = mapBatteryVoltageToPercentage(batteryVoltage);

  delay(5000);  // 5초 딜레이
}
