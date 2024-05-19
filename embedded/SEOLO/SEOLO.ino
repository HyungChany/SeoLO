#include <Arduino.h>
#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEServer.h>
#include <AccelStepper.h>
#include <Preferences.h>
#include <AESLib.h>
#include "base64.h"
#include "libb64/cdecode.h"
#include <sstream>  // istringstream을 사용하기 위해 포함

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
bool isAdPlaying = false;
BLEAdvertising *pAdvertising;

bool isCheckCodeAvailableRunning = false;  // 실행 중인지 여부를 추적하는 플래그

// MOTOR 설정
#define IN1 12  // ULN2003의 IN1 핀
#define IN2 11  // ULN2003의 IN2 핀
#define IN3 10  // ULN2003의 IN3 핀
#define IN4 9   // ULN2003의 IN4 핀
AccelStepper stepper(8, IN1, IN3, IN2, IN4);

void disableMotor() {
  // 모터 핀을 LOW로 설정하여 모터를 비활성화
  digitalWrite(IN1, LOW);
  digitalWrite(IN2, LOW);
  digitalWrite(IN3, LOW);
  digitalWrite(IN4, LOW);
}

// LOCK 상태
const byte masterKey[4] = { 0xA3, 0x84, 0x1E, 0x27 };
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
BLEServer *pServer = nullptr;
BLEService *messageService = nullptr;
BLECharacteristic *stringCharacteristic = nullptr;

String receivedString = "";
String companyCode = "";
String code = "";
String token = "";
String machine = "";
String user = "";
String feUID = "";
int battery = 0;

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

// BLE 서버 콜백
class MyServerCallbacks : public BLEServerCallbacks {
public:
  std::map<uint16_t, BLECharacteristic *> clientCharacteristics;

  void onConnect(BLEServer *pServer, esp_ble_gatts_cb_param_t *param) override {
    uint16_t connId = param->connect.conn_id;
    BLECharacteristic *characteristic = pServer->getServiceByUUID(BLEUUID(SERVICE_UUID))->getCharacteristic(BLEUUID(CHARACTERISTIC_UUID));
    clientCharacteristics[connId] = characteristic;
    pServer->startAdvertising();
    Serial.println("Client connected, connId: " + String(connId));
  }

  void onDisconnect(BLEServer *pServer, esp_ble_gatts_cb_param_t *param) override {
    uint16_t connId = param->disconnect.conn_id;
    clientCharacteristics.erase(connId);
    pServer->startAdvertising();
    isAdPlaying = false;
    Serial.println("Client disconnected, connId: " + String(connId));
  }
};

MyServerCallbacks *serverCallbacks;

// Helper function to decode Base64
std::string base64Decode(const std::string &toBeDecoded) {
  int bufferLength = (toBeDecoded.length() * 3) / 4;
  std::string decoded;
  decoded.resize(bufferLength);

  base64_decodestate s;
  base64_init_decodestate(&s);

  int decodedLength = base64_decode_block(toBeDecoded.c_str(), toBeDecoded.length(), &decoded[0], &s);
  decoded.resize(decodedLength);

  return decoded;
}

// Helper function to decrypt AES-128 ECB
std::string decryptAES128ECB(const std::string &ciphertext, const std::string &base64Key) {
  std::string decodedCiphertext = base64Decode(ciphertext);
  std::string key = base64Decode(base64Key);

  AES aes;
  byte keyBytes[16];
  byte plaintextBytes[16];

  memcpy(keyBytes, key.data(), 16);
  aes.set_key(keyBytes, sizeof(keyBytes));
  aes.decrypt(reinterpret_cast<const byte *>(decodedCiphertext.data()), plaintextBytes);

  return std::string(reinterpret_cast<char *>(plaintextBytes), 16);
}

void notifyClient(uint16_t connId, const String &message) {
  BLECharacteristic *characteristic = serverCallbacks->clientCharacteristics[connId];
  if (characteristic) {
    characteristic->setValue(message.c_str());
    characteristic->notify();
    Serial.println("Sent to connId " + String(connId) + ": " + message);
  } else {
    Serial.println("Failed to find characteristic for connId " + String(connId));
  }
}

void checkCodeAvailable(String code, String token, String machine, String user, String feUID, uint16_t connId) {
  String message = "";

  if (isCheckCodeAvailableRunning) {
    return;  // 다른 checkCodeAvailable 함수가 실행 중이면 함수를 종료합니다.
  }
  isCheckCodeAvailableRunning = true;  // 함수가 실행 중임을 표시합니다.

  std::string decodedToken = base64Decode(token.c_str());
  std::string decodedKey = base64Decode(AES_KEY);
  std::string decryptedToken = decryptAES128ECB(decodedToken, decodedKey);
  String decryptedTokenString = String(decryptedToken.c_str());

  bool actionPerformed = false;  // 작업 수행 여부를 추적하는 플래그

  if (code == "INIT") {
    if (savedToken != "" && !actionPerformed) {
      message += "CHECK";
      message += ",";
      message += UID;
      message += ",";
      message += savedMachine;
      actionPerformed = true;
    } else if (machine == "" && !actionPerformed) {
      message += "WRITE";
      message += ",";
      message += UID;
      message += ",";
      message += machine;
      actionPerformed = true;
    } else if (machine != "" && !actionPerformed) {
      message += "WRITED";
      message += ",";
      message += UID;
      message += ",";
      message += machine;
      savedMachine = machine;
      actionPerformed = true;
    }
  } else if (code == "LOCKED") {
    if (savedToken == "" && !actionPerformed) {
      message += "ALERT";
      message += ",";
      message += UID;
      message += ",";
      message += savedMachine;
      actionPerformed = true;
    } else if (savedToken == token && !actionPerformed) {
      message += "UNLOCK";
      message += ",";
      message += UID;
      message += ",";
      message += savedMachine;
      actionPerformed = true;
      stepper.moveTo(stepper.currentPosition() - 2200);
      savedMachine = "";
      savedToken = "";
    } else if (!actionPerformed) {
      message += "CHECK";
      message += ",";
      message += UID;
      message += ",";
      message += savedMachine;
      actionPerformed = true;
    }
  } else if (code == "LOCK") {
    if (token != "" && savedToken == "" && feUID == UID && !actionPerformed) {
      savedMachine = machine;
      savedToken = token;
      message += "LOCKED";
      message += ",";
      message += UID;
      message += ",";
      message += savedMachine;
      actionPerformed = true;
      stepper.moveTo(stepper.currentPosition() + 2200);
    } else if (token == savedToken && feUID == UID && !actionPerformed) {
      savedMachine = machine;
      savedToken = token;
      message += "LOCKED";
      message += ",";
      message += UID;
      message += ",";
      message += savedMachine;
      actionPerformed = true;
      stepper.moveTo(stepper.currentPosition() + 2200);
    } else if (!actionPerformed) {
      message += ",";
      message += ",";
      actionPerformed = true;
    }
  } else if (!actionPerformed) {
    message += ",";
    message += ",";
    actionPerformed = true;
  }

  if (actionPerformed) {
    int adcValue = analogRead(analogPin);
    float batteryVoltage = (adcValue * referenceVoltage / resolution) * voltageDivider;
    int battery = mapBatteryVoltageToPercentage(batteryVoltage);

    message += ",";
    message += battery;
    message += ",";
    message += user;

    notifyClient(connId, message);
  }

  while (stepper.distanceToGo() != 0) {
    stepper.run();
  }
  disableMotor();  // 모터 사용 완료 후 비활성화

  isCheckCodeAvailableRunning = false;  // 함수가 실행을 마쳤음을 표시합니다.
}

class MyCallbacks : public BLECharacteristicCallbacks {
public:
  void onWrite(BLECharacteristic *characteristic) override {
    std::string receivedString = characteristic->getValue();

    if (receivedString.empty()) {
      characteristic->setValue(",,,,");
      return;
    }

    std::vector<std::string> tokens = splitString(receivedString, ',');

    if (tokens.size() < 6) {
      characteristic->setValue(",,,,");
      return;
    }

    companyCode = tokens[0].c_str();
    token = tokens[1].c_str();
    machine = tokens[2].c_str();
    user = tokens[3].c_str();
    feUID = tokens[4].c_str();
    code = tokens[5].c_str();

    for (const auto &pair : serverCallbacks->clientCharacteristics) {
      if (pair.second == characteristic) {
        uint16_t connId = pair.first;
        checkCodeAvailable(code, token, machine, user, feUID, connId);
        break;
      }
    }
  }
};

void setup(void) {
  Serial.begin(115200);

  BLEDevice::init("SEOLO LOCK 777");

  serverCallbacks = new MyServerCallbacks();
  pServer = BLEDevice::createServer();
  pServer->setCallbacks(serverCallbacks);

  BLEService *pService = pServer->createService(BLEUUID(SERVICE_UUID));

  pCharacteristic = pService->createCharacteristic(
    BLEUUID(CHARACTERISTIC_UUID),
    BLECharacteristic::PROPERTY_READ | BLECharacteristic::PROPERTY_WRITE | BLECharacteristic::PROPERTY_NOTIFY);

  pCharacteristic->setCallbacks(new MyCallbacks());

  pService->start();

  pAdvertising = BLEDevice::getAdvertising();
  pAdvertising->addServiceUUID(pService->getUUID());
  pAdvertising->setScanResponse(false);
  pAdvertising->start();

  stringCharacteristic = pService->getCharacteristic(BLEUUID(CHARACTERISTIC_UUID));
  battery = 0;

  preferences.begin("nfc-data", false);
  lockState = preferences.getBool("lockState", false);
  savedToken = preferences.getString("savedToken", "");
  savedMachine = preferences.getString("savedMachine", "");

  pinMode(IN1, OUTPUT);
  pinMode(IN2, OUTPUT);
  pinMode(IN3, OUTPUT);
  pinMode(IN4, OUTPUT);

  stepper.setMaxSpeed(2000);
  stepper.setAcceleration(1500);
}

void loop(void) {
  int adcValue = analogRead(analogPin);
  float batteryVoltage = (adcValue * referenceVoltage / resolution) * voltageDivider;
  battery = mapBatteryVoltageToPercentage(batteryVoltage);

  if (!isAdPlaying) {
    pAdvertising->start();
    isAdPlaying = true;
  }

  delay(5000);  // 5초 딜레이
}
