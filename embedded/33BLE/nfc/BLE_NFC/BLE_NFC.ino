// 라이브러리 가져오기
#include <ArduinoBLE.h>   
// Arduino 보드를 BLE(Bluetooth Low Energy) 장치로 만들기 위한 라이브러리
// Arduino와 BLE 중앙 장치 또는 주변장치로 동작 가능

#include <SPI.h>          
// 시리얼 페리피럴 인터페이스 프로토콜을 사용하여 Arduino와 다른 장치 간에 통신할 때 사용
// SPI 버스를 초기화하고 SPI 통신을 설정하는 데 사용

#include <MFRC522.h>      
// MFRC522 라이브러리는 RFID 리더 모듈인 MFRC522와 통신하기 위한 라이브러리
// Arduino가 MFRC522 모듈과 통신하고 RFID 태그의 UID(고유 식별자)를 읽을 수 있음

#include <Adafruit_PN532.h>
// NFC(Near Field Communication) 기능을 제어하기 위한 라이브러리입니다. 
// Arduino가 NFC 태그를 감지하고, NDEF(NFC Data Exchange Format) 메시지를 생성하고 읽을 수 있음

// Arduino NANO 33 BLE Serial Number
// 09:5f:61:d3:0f:43
// Device ID 0: EED079D5
// Device ID 1: 55DDA33
// Device Address 0: 61D30F43
// Device Address 1: D4FE095F


// 핀 설정들
#define RST_RFID_PIN  9   // RFID RST
#define SDA_PIN 10        // SDA
#define MOSI_PIN 11       // MOSI
#define MISO_PIN 12       // MISO
#define SCK_PIN 13        // SCK


MFRC522 mfrc522(SDA_PIN, RST_RFID_PIN);
Adafruit_PN532 nfc(PN532_SCK, PN532_MISO, PN532_MOSI, PN532_SS);

String arduinoUID;

void setup(void) {
  Serial.begin(9600);
  while (!Serial);

  BLE.begin();
  Serial.println("SEOLO Service started");

  SPI.begin();
  mfrc522.PCD_Init(); // MFRC522 RFID reader 초기화
  nfc.begin(); // PN532 NFC reader 초기화
  uint32_t versiondata = nfc.getFirmwareVersion();
  if (!versiondata) {
    Serial.println("Didn't find PN53x board");
    while (1);
  }

// 준비 됨
  mfrc522.PCD_SetAntennaGain(mfrc522.RxGain_max);
  Serial.println("RFID and NFC readers initialized");

// arduino uid 넣기
  arduinoUID = "09:5f:61:d3:0f:43"; 
  Serial.print("Arduino's UID: ");
  Serial.println(arduinoUID);
}

void loop(void) {
  // RFID 카드 검색
  if (nfc.tagPresent()) {
    // 카드가 발견되면 UID를 읽음
    NfcTag tag = nfc.read();
    String uid = tag.getUidString();

    Serial.print("RFID 카드 UID: ");
    Serial.println(uid);

    // NDEF 메시지 생성
    NdefMessage message;

    // 임시로 rfid 카드의 uid를 저장해두고 이 로직 실행해보면 될듯함.
    if (uid == arduinoUID) {
      // UID가 아두이노의 UID와 일치하는 경우 "아두이노의 UID + INIT" 추가
      message.addUid(tag.getUid(), tag.getUidLength()); // RFID 카드의 UID 추가
      message.addTextRecord("INIT"); // "INIT" 문자열 추가
    } else {
      // UID가 아두이노의 UID와 일치하지 않는 경우 "FAILED" 추가
      message.addTextRecord("FAILED"); // "FAILED" 문자열 추가
    }

    // NDEF 메시지 출력
    Serial.println("NDEF 메시지 생성됨");
    Serial.print("NDEF 메시지 내용: ");
    Serial.println(message.getJson());

    // BLE를 통해 NDEF 메시지 전송
    BLE.send(message.getJson());
    Serial.println("BLE를 통해 NDEF 메시지 전송됨");

    // 다음 카드 스캔까지 잠시 대기
    delay(1000);
  }
}