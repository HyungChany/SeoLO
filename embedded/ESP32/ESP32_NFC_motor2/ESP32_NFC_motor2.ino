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

byte masterKey[4] = {0xE7, 0xE2, 0x02, 0xE7};
byte storedUID[7] = {0};  // 최대 7바이트 UID를 저장할 배열
bool uidStored = false;
bool lockState = false;  // 잠금 상태를 저장하는 변수

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
    while (1); // 무한 루프로 프로그램을 멈춤
  }

  nfc.SAMConfig();
  Serial.println("NFC 카드를 기다리는 중...");

  preferences.begin("nfc-data", false);  // preferences 초기화
  uidStored = preferences.getBool("uidStored", false);  // uidStored 값 불러오기
  lockState = preferences.getBool("lockState", false);  // lockState 값 불러오기
  if (uidStored) {
    size_t len = preferences.getBytesLength("storedUID");
    if (len == sizeof(storedUID)) {
      preferences.getBytes("storedUID", storedUID, len);
    }
  }
}

void loop(void) {
  uint8_t success;
  uint8_t uid[7];
  uint8_t uidLength;

  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);

  if (success) {
    Serial.print("UID 길이: "); Serial.print(uidLength, DEC); Serial.print(" 바이트 /");
    Serial.print(" UID 값:");
    for (uint8_t i = 0; i < uidLength; i++) {
      Serial.print(" 0x"); Serial.print(uid[i], HEX);
    }
    Serial.println("");

    if (lockState) {  // 잠금 상태일 때만 실행
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
    } else {  // 잠금 해제 상태일 때 실행
      if (!uidStored) {
        memcpy(storedUID, uid, uidLength);
        uidStored = true;
        preferences.putBool("uidStored", uidStored);
        preferences.putBytes("storedUID", storedUID, uidLength);
        lockState = true;  // 카드 저장과 동시에 잠금 상태로 전환
        preferences.putBool("lockState", lockState);
        Serial.println("저장. 잠금 상태 활성화.");
        stepper.moveTo(700);
      }
    }

    while (stepper.distanceToGo() != 0) {
      stepper.run();
    }

    delay(1000);
  }
}

void clearStoredUID() {
  memset(storedUID, 0, sizeof(storedUID));
  uidStored = false;
  lockState = false;  // UID 초기화와 동시에 잠금 해제
  preferences.putBool("uidStored", uidStored);
  preferences.putBool("lockState", lockState);
  preferences.remove("storedUID");
}