#include <WiFi.h>

const char* ssid = "와이파이이름";
const char* password = "와이파이비번";
unsigned long lastAttemptTime = 0;
int attemptInterval = 10000; // 10초 간격으로 재연결 시도
int resetInterval = 3; // 3번 연속 실패 시 리셋
int attemptCount = 0;

void setup() {
  Serial.begin(115200);
  connectToWiFi();
}

void loop() {
  checkWiFiConnection();
  delay(1000); // 1초마다 상태 확인
}

void connectToWiFi() {
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED && millis() - lastAttemptTime < attemptInterval) {
    delay(1000);
    Serial.print(".");
  }
  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("연결.");
    Serial.println("IP 주소: ");
    Serial.println(WiFi.localIP());
    attemptCount = 0; // 연결 성공 시 카운트 초기화
  } else {
    Serial.println("연결 실패. 다시 시도.");
    attemptCount++;
    if (attemptCount >= resetInterval) {
      Serial.println("연속 재연결 실패. 다시시작...");
      ESP.restart(); // ESP32 리셋
    }
  }
  lastAttemptTime = millis(); // 마지막 시도 시간 업데이트
}

void checkWiFiConnection() {
  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("연결 끊김. 다시연결...");
    connectToWiFi();
  }
}
