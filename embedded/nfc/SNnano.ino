#include <SPI.h>
#include <MFRC522.h>
 
#define RST_PIN   9                            
#define SS_PIN    10                           
                                               
 
MFRC522 mfrc(SS_PIN, RST_PIN);

byte masterKey[4] = {0x01, 0x23, 0x45, 0x67};
byte storedUID[4] = {0x00, 0x00, 0x00, 0x00};
bool uidStored  = false;

void setup(){
  Serial.begin(9600);                         
  SPI.begin();                                
                                              
  mfrc.PCD_Init();                               
}
 
void loop(){
  if ( !mfrc.PICC_IsNewCardPresent() || !mfrc.PICC_ReadCardSerial() ) {   
                                               // 태그 접촉이 되지 않았을때 또는 ID가 읽혀지지 않았을때
    delay(1000);                                
    return;                                   
  } 

  Serial.print("Card UID (Decimal): ");
  for (byte i = 0; i < 4; i++) {
    Serial.print(mfrc.uid.uidByte[i]);
    Serial.print(" ");
  }
  Serial.println();

  Serial.print("아두이노 시리얼 코드: ");
  Serial.println(SerialNumber());

  // Serial.print("Card UID (Hexadecimal): ");
  // for (byte i = 0; i < 4; i++) {
  //   Serial.print("0x");
  //   if (mfrc.uid.uidByte[i] < 0x10) Serial.print("0");
  //   Serial.print(mfrc.uid.uidByte[i], HEX);
  //   Serial.print(" ");
  // }
  // Serial.println();  

  if (memcmp(masterKey, mfrc.uid.uidByte, 4) == 0) {
    // 마스터 키와 일치하면 저장된 UID 값 초기화
    memset(storedUID, 0x00, 4);
    uidStored = false;
    Serial.println("마스터키, 초기화");
  } else {
    if (!uidStored) {
      // 저장된 UID 값이 없는 경우
      memcpy(storedUID, mfrc.uid.uidByte, 4);
      Serial.println("저장");
      uidStored = true;
    } else {
      // 저장된 UID 값이 있는 경우
      if (memcmp(storedUID, mfrc.uid.uidByte, 4) == 0) {
        // 저장된 UID 값과 태그된 UID 값이 일치하는 경우
        Serial.println("일치, 초기화");
        memset(storedUID, 0x00, 4);
        uidStored = false;
      } else {
        // 저장된 UID 값과 태그된 UID 값이 일치하지 않는 경우
        Serial.println("불일치");
      }
    }
  }
}

String SerialNumber() {
  char serialNumber[10];
  sprintf(serialNumber, "%X", ESP.getChipModel());
  return String(serialNumber);
}
