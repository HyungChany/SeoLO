const int analogPin = A3;  // 아두이노 나노 ESP32의 A3 아날로그 입력 핀 (GPIO 15)
const float voltageDivider = 2.0;  // 전압 분배 계수 (10K옴 + 10K옴 = 입력 전압의 반)
const float referenceVoltage = 3.3;  // ESP32의 기준 전압
const int resolution = 4095;  // ESP32의 ADC 해상도 (0-4095)

void setup() {
  Serial.begin(115200);
}

void loop() {
  int adcValue = analogRead(analogPin);
  float batteryVoltage = (adcValue * referenceVoltage / resolution) * voltageDivider;
  
  int batteryPercentage = mapBatteryVoltageToPercentage(batteryVoltage);
  Serial.print("배터리: ");
  Serial.print(batteryVoltage);
  Serial.print(" V - ");
  Serial.print(batteryPercentage);
  Serial.println("%");
  
  delay(5000);  // 2초마다 측정
}

int mapBatteryVoltageToPercentage(float voltage) {
    if (voltage >= 4.09) return 100;  // 100%
    if (voltage >= 4.0) return 95;  // 95%
    if (voltage >= 3.9) return 90;  // 90%
    if (voltage >= 3.8) return 85;  // 85%
    if (voltage >= 3.7) return 75;  // 75%
    if (voltage >= 3.6) return 65;  // 65%
    if (voltage >= 3.5) return 55;  // 55%
    if (voltage >= 3.4) return 45;  // 45%
    if (voltage >= 3.3) return 35;  // 35%
    if (voltage >= 3.2) return 25;  // 25%
    if (voltage >= 3.1) return 15;  // 15%
    if (voltage >= 3.0) return 5;   // 5%
    return 0;  // 이외의 경우 0%
}
