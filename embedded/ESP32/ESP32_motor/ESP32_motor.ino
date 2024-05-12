#include <AccelStepper.h>

#define IN1 12 // ULN2003의 IN1 핀
#define IN2 11 // ULN2003의 IN2 핀
#define IN3 10 // ULN2003의 IN3 핀
#define IN4 9  // ULN2003의 IN4 핀

AccelStepper stepper(8, IN1, IN3, IN2, IN4);

void setup() {
  pinMode(IN1, OUTPUT);
  pinMode(IN2, OUTPUT);
  pinMode(IN3, OUTPUT);
  pinMode(IN4, OUTPUT);

  // 시리얼 통신 시작
  Serial.begin(9600);

  // 모터 설정
  stepper.setMaxSpeed(500);
  stepper.setAcceleration(200);

  // 모터와 LED 테스트를 위한 초기 설정
  Serial.println("Starting motor and LED test...");
}

void loop() {
  // 모터를 한 방향으로 2048 스텝 이동
  stepper.moveTo(2048);
  while (stepper.distanceToGo() != 0) {
    stepper.run();
    updateLEDs();
  }

  delay(1000); // 짧은 대기

  // 모터를 반대 방향으로 2048 스텝 이동
  stepper.moveTo(-2048);
  while (stepper.distanceToGo() != 0) {
    stepper.run();
    updateLEDs();
  }

  delay(1000); // 짧은 대기
}

// LED 업데이트 함수
void updateLEDs() {
  digitalWrite(IN1, digitalRead(IN1));
  digitalWrite(IN2, digitalRead(IN2));
  digitalWrite(IN3, digitalRead(IN3));
  digitalWrite(IN4, digitalRead(IN4));
}
