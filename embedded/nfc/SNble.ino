#include <ArduinoBLE.h>

void setup() {
  Serial.begin(9600);
  while (!Serial); // 시리얼 통신이 준비될 때까지 대기
  BLE.begin();
}

void loop() {
  String bleAddress = getBLEAddress();
  Serial.println(bleAddress);
  delay(1000); // 한 번에 한 번씩 출력되도록 딜레이 추가
}

String getBLEAddress() {
  // BLE 주소를 문자열로 변환하여 반환
  String address = BLE.address();
  return address;
}