#include <Wire.h>
#include <Adafruit_PN532.h>
#include <AccelStepper.h>
#include <Preferences.h>

#define SDA_PIN 4  
#define SCL_PIN 5  

Adafruit_PN532 nfc(SDA_PIN, SCL_PIN);

#define IN1 12 // ULN2003의 IN1 핀
#define IN2 11 // ULN2003의 IN2 핀
#define IN3 10 // ULN2003의 IN3 핀
#define IN4 9  // ULN2003의 IN4 핀

AccelStepper stepper(8, IN1, IN3, IN2, IN4);
Preferences preferences;

const int analogPin = A3;  // 아날로그 입력 핀 (GPIO 15)
const float voltageDivider = 2.0;  // 전압 분배 계수
const float referenceVoltage = 3.3;  // 기준 전압
const int resolution = 4095;  // ADC 해상도

byte masterKey[4] = {0xE7, 0xE2, 0x02, 0xE7};
byte storedUID[7] = {0};
bool uidStored = false;
bool lockState = false;

void setup(void) {
  Serial.begin(115200);
  Serial.println("시스템 시작!");

  pinMode(IN1, OUTPUT);
  pinMode(IN2, OUTPUT);
  pinMode(IN3, OUTPUT);
  pinMode(IN4, OUTPUT);

  stepper.setMaxSpeed(500);
  stepper.setAcceleration(200);

  nfc.begin();
  uint32_t versiondata = nfc.getFirmwareVersion();
  if (!versiondata) {
    Serial.print("PN53x 보드를 찾을 수 없습니다");
    while (1);
  }

  nfc.SAMConfig();
  Serial.println("NFC 카드를 기다리는 중...");

  preferences.begin("nfc-data", false);
  uidStored = preferences.getBool("uidStored", false);
  lockState = preferences.getBool("lockState", false);
  if (uidStored) {
    size_t len = preferences.getBytesLength("storedUID");
    if (len == sizeof(storedUID)) {
      preferences.getBytes("storedUID", storedUID, len);
    }
  }
}

void loop(void) {
  readNFCAndBattery();
  while (stepper.distanceToGo() != 0) {
    stepper.run();
  }
  disableMotor();  // 모터 사용 완료 후 비활성화
  delay(1000);  // 측정 간격 조절
}

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
  int batteryPercentage = mapBatteryVoltageToPercentage(batteryVoltage);
  Serial.print("배터리: ");
  Serial.print(batteryVoltage);
  Serial.print(" V - ");
  Serial.print(batteryPercentage);
  Serial.println("%");

  if (success) {
    Serial.print("UID 길이: "); Serial.print(uidLength, DEC); Serial.print(" 바이트 /");
    Serial.print(" UID 값:");
    for (uint8_t i = 0; i < uidLength; i++) {
      Serial.print(" 0x"); Serial.print(uid[i], HEX);
    }
    Serial.println("");

    handleNFC(uid, uidLength);
  }
}

void handleNFC(uint8_t *uid, uint8_t uidLength) {
  if (lockState) {
    if (memcmp(masterKey, uid, 4) == 0) {
      Serial.println("마스터 키/ 초기화. 잠금 해제.");
      clearStoredUID();
      stepper.moveTo(-700);
    } else if (memcmp(storedUID, uid, uidLength) == 0) {
      Serial.println("일치. 초기화.");
      clearStoredUID();
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

int mapBatteryVoltageToPercentage(float voltage) {
    if (voltage >= 4.09) return 100;
    if (voltage >= 4.0) return 95;
    if (voltage >= 3.9) return 90;
    if (voltage >= 3.8) return 85;
    if (voltage >= 3.7) return 75;
    if (voltage >= 3.6) return 65;
    if (voltage >= 3.5) return 55;
    if (voltage >= 3.4) return 45;
    if (voltage >= 3.3) return 35;
    if (voltage >= 3.2) return 25;
    if (voltage >= 3.1) return 15;
    if (voltage >= 3.0) return 5;
    return 0;
}

void clearStoredUID() {
  memset(storedUID, 0, sizeof(storedUID));
  uidStored = false;
  lockState = false;
  preferences.putBool("uidStored", uidStored);
  preferences.putBool("lockState", lockState);
  preferences.remove("storedUID");
}
