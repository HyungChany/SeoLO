#include <avr/boot.h>

void setup() {
  Serial.begin(9600);
  while (!Serial)
    ;  // 시리얼 포트가 준비될 때까지 대기
}

void loop() {
  // 아두이노 보드의 유일한 값을 생성
  unsigned long uniqueId = getUniqueId();
  Serial.println(uniqueId);
  delay(1000);  // 한 번에 한 번씩 출력되도록 딜레이 추가
}

unsigned long getUniqueId() {
  // 아두이노 보드의 플래시 메모리 영역에 저장된 고유 식별자를 읽어와서 사용
  unsigned long uniqueId = 0;
  for (int i = 0; i < 4; i++) {
    uniqueId |= (boot_signature_byte_get(i) << (i * 8));
  }
  return uniqueId;
}