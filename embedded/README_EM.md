## embedded

BLE기반 자물쇠를 제공합니다.<br>

### 제공 기능
- BLE OPEN/CLOSE
- NFC 마스터키
- C타입 충전
- Magnetic Sensor 활용한 잠금 확인

### 회로도
- 회로도
  ![회로도](SEOLO\SEOLO.png)
- 스케메틱
  ![스케메틱](SEOLO\SEOLO_schem.png)
- PCB
  ![PCB](SEOLO\SEOLO_pcb.ps.png)

### 사용 라이브러리
```
Arduino
base64
libb64/cdecode
Wire
sstream

ESP32_BLE_Arduino
AccelStepper
Preferences
AESLib
Adafruit_PN532
```

### 기여
- 조형찬 : NFC, MOTOR, Magnetic Sensor, C-type, 조립
- 오유진 : BLE, Base64, AES128 (Core Logic)
- 이현비 : 3D Modeling
- 오민상 : 남땜 지원
