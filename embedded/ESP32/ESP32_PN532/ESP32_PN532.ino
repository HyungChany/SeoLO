#include <Wire.h>
#include <Adafruit_PN532.h>

#define SDA_PIN 4 
#define SCL_PIN 5  

Adafruit_PN532 nfc(SDA_PIN, SCL_PIN);

byte masterKey[4] = {0x75, 0x9B, 0x7D, 0xA8};
byte storedUID[7] = {0};  // 최대 7바이트 UID를 저장할 배열
bool uidStored = false;

void setup(void) {
  Serial.begin(115200);
  Serial.println("Hello!");

  nfc.begin();
  uint32_t versiondata = nfc.getFirmwareVersion();
  if (!versiondata) {
    Serial.print("PN53x 보드를 찾을 수 없습니다");
    while (1); // 무한 루프로 프로그램을 멈춤
  }

  nfc.SAMConfig();
  Serial.println("NFC 카드를 기다리는 중...");
}

void loop(void) {
  uint8_t success;
  uint8_t uid[7];  // 최대 7바이트 UID를 저장할 배열
  uint8_t uidLength;  // UID의 길이

  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);

  if (success) {
    Serial.print("UID 길이: "); Serial.print(uidLength, DEC); Serial.print(" bytes /");
    Serial.print(" UID 값:");
    for (uint8_t i = 0; i < uidLength; i++) {
      Serial.print(" 0x"); Serial.print(uid[i], HEX);
    }
    Serial.println("");

    if (memcmp(masterKey, uid, 4) == 0) {
      Serial.println("마스터 키/ 초기화.");
      memset(storedUID, 0, sizeof(storedUID));
      uidStored = false;
    } else {
      if (!uidStored) {
        memcpy(storedUID, uid, uidLength);
        uidStored = true;
        Serial.println("저장.");
      } else {
        if (memcmp(storedUID, uid, uidLength) == 0) {
          Serial.println("일치/ 초기화.");
          memset(storedUID, 0, sizeof(storedUID));
          uidStored = false;
        } else {
          Serial.println("불일치.");
        }
      }
    }

    delay(1000);  // 다음 카드 읽기를 위해 1초 동안 기다림
  }
}
