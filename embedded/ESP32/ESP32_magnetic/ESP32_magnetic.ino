#define HALL_SENSOR_PIN          2                  // 홀 센서 2번핀에 연결

void setup() 
{
  pinMode(HALL_SENSOR_PIN, INPUT);                  // 2번핀 입력으로 설정
  Serial.begin(115200);                             // 시리얼 통신 시작, 보레이트 115200
}

void loop() 
{
  Serial.print("Hall Sensor = ");                   // 시리얼 모니터 "Hall Sensor = " 출력

  bool sensor_value = digitalRead(HALL_SENSOR_PIN); // 센서핀 상태 검사

  if (sensor_value == 1) Serial.println("OFF");     // 자기장이 감지되지 않으면 OFF 출력, 초기값 = 1 
  else                   Serial.println("ON");      // 자기장이 감지되면 ON 출력

  delay(1000);                                      // 1초간 기다림
}