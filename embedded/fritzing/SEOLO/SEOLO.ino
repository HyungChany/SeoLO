#include <sstream>
#include <vector>
#include <string>
#include <Wire.h>
#include <Preferences.h>


#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEServer.h>
#include <Adafruit_PN532.h>
#include <AccelStepper.h>


// String -> Char
std::vector<std::string> splitString(const std::string &s, char delimiter)
{
    std::vector<std::string> tokens;
    std::string token;
    std::istringstream tokenStream(s);
    while (std::getline(tokenStream, token, delimiter))
    {
        tokens.push_back(token);
    }
    return tokens;
}


// BLE ??
#define SERVICE_UUID "20240520-C104-C104-C104-012345678910"
#define CHARACTERISTIC_UUID "20240521-C104-C104-C104-012345678910"
#define AUTHENTICATION_CODE "SFY001KOR"
#define UID "1DA24G10"
bool isCheckCodeAvailableRunning = false; // ?? ??? ??? ???? ???


// NFC ??
#define SDA_PIN 4  
#define SCL_PIN 5 
Adafruit_PN532 nfc(SDA_PIN, SCL_PIN);


// MOTOR ??
#define IN1 12 // ULN2003? IN1 ?
#define IN2 11 // ULN2003? IN2 ?
#define IN3 10 // ULN2003? IN3 ?
#define IN4 9  // ULN2003? IN4 ?
AccelStepper stepper(8, IN1, IN3, IN2, IN4);


// LOCK ??
byte masterKey[4] = {0xE7, 0xE2, 0x02, 0xE7};
byte storedUID[7] = {0};
bool uidStored = false;
bool lockState = false;


// ?? ??? ?????? ????? ??
Preferences preferences;


// BATTERY ??
const int analogPin = A3;  // ???? ?? ESP32? A3 ???? ?? ? (GPIO 15)
const float voltageDivider = 2.0;  // ?? ?? ?? (10K? + 10K? = ?? ??? ?)
const float referenceVoltage = 3.3;  // ESP32? ?? ??
const int resolution = 4095;  // ESP32? ADC ??? (0-4095)



String savedToken = "";
String savedMachine = "";
BLECharacteristic *pCharacteristic;
BLEServer *pServer = NULL;
BLEService *messageService = NULL;
BLECharacteristic *stringCharacteristic = NULL;


String receivedString = "";
String companyCode = "";
String code = "";
String token = "";
String machine = "";
String user = "";
int battery = 0;


class MyCallbacks : public BLECharacteristicCallbacks
{
public:
    void onWrite(BLECharacteristic *characteristic)
    {
        std::string receivedString = characteristic->getValue();


        // ???? ?? ?? ????
        if (receivedString.empty())
        {
            stringCharacteristic->setValue(",,,,");
            Serial.println("Empty data received");
            return;
        }


        // ??? ???? ??
        std::vector<std::string> tokens = splitString(receivedString, ',');


        // ??? 5? ??? ?? ????
        if (tokens.size() < 5)
        {
            stringCharacteristic->setValue(",,,,");
            Serial.println("Insufficient data received");
            return;
        }


        // ???? ???? ??
        companyCode = tokens[0].c_str();
        code = tokens[1].c_str();
        token = tokens[2].c_str();
        machine = tokens[3].c_str();
        user = tokens[4].c_str();


        Serial.println(companyCode.c_str());
        Serial.println(code.c_str());
        Serial.println(token.c_str());
        Serial.println(machine.c_str());
        Serial.println(user.c_str());


        // ?? ??? ?? ??? ???? ?! ?????? ?? ??? ????, ?? ?? ??? ?????.
        if (pServer->getConnectedCount() > 0)
        {
            std::map<uint16_t, conn_status_t> peerDevices = pServer->getPeerDevices(false);
            for (auto conn : peerDevices)
            {
                uint16_t connId = conn.first;
                conn_status_t connStatus = conn.second;
                if (connStatus.connected && connStatus.peer_device != nullptr)
                {
                    // ?????? ?? ??? ??? ?? ??? ??
                    if (companyCode != AUTHENTICATION_CODE)
                    {
                        // ?? ??? ?? ?? ?????? ??? ??
                        pServer->disconnect(connId);
                    }
                    else
                    {
                        checkCodeAvailable(code, token, machine, user);
                    }
                }
            }
        }
    }
};


class MyServerCallbacks : public BLEServerCallbacks
{
public:
    void onConnect(BLEServer *pServer)
    {
        int connectedCount = pServer->getConnectedCount() + 1;
        Serial.print("Connected devices count: ");
        Serial.println(connectedCount);
        pServer->startAdvertising();
    }


    void onDisconnect(BLEServer *pServer)
    {
        int connectedCount = pServer->getConnectedCount() - 1;
        Serial.print("Connected devices count: ");
        Serial.println(connectedCount);
        pServer->startAdvertising();
    }
};


MyServerCallbacks serverCallbacks;


void checkCodeAvailable(String code, String token, String machine, String user)
{
    String message = "";


    if(isCheckCodeAvailableRunning) {
        Serial.println("checkCodeAvailable is already running.");
        return; // ?? checkCodeAvailable ??? ?? ??? ??? ?????.
    }
    isCheckCodeAvailableRunning = true; // ??? ?? ??? ?????.
    
    if (code == "INIT")
    {
        if (savedToken != "")
        {
            // "CHECK, UID, MachineId, BATTERY" ??
            message += "CHECK";
            message += ",";
            message += UID;
            message += ",";
            message += savedMachine;
        }
        else
        {
            // "WRITE, UID, BATTERY" ??
            message += "WRITE";
            message += ",";
            message += UID;
            message += ",";
        }
    }
    else if (code == "LOCKED")
    {
        if (savedToken == "")
        {
            // "ALERT, BATTERY" ??
            message += "ALERT";
            message += ",";
            message += ",";
        }
        else if (savedToken == token)
        {
            // "UNLOCK, UID, BATTERY" ??
            message += "UNLOCK";
            message += ",";
            message += UID;
            message += ",";


            // ??? ??
            stepper.moveTo(-700);


            // ??? ?? ??
            savedToken = "";
            savedMachine = "";
        }
        else
        {
            // "CHECK, UID, machineId, BATTERY" ??
            message += "CHECK";
            message += ",";
            message += UID;
            message += ",";
            message += savedMachine;
        }
    }
    else if (code == "LOCK")
    {
        if (token != "" && savedToken == "")
        {
            // ???? ?? ??
            savedMachine = machine;
            savedToken = token;
            // ??? ???
            stepper.moveTo(700);


            // ???? ??? ??("LOCKED",  "UID", "BATTERY")
            message += "LOCKED";
            message += ",";
            message += UID;
            message += ",";
        }
        else if (token == savedToken)
        {
            // ??? ??
            stepper.moveTo(700);


            // ???? ??? ??("LOCKED", "UID", "BATTERY")
            message += "LOCKED";
            message += ",";
            message += UID;
            message += ",";
            message += savedMachine;
        }
        else
        {
            message += ",";
            message += ",";
        }
    } else {
        message += ",";
        message += ",";
    }


    int adcValue = analogRead(analogPin);
    float batteryVoltage = (adcValue * referenceVoltage / resolution) * voltageDivider;
    int battery = mapBatteryVoltageToPercentage(batteryVoltage);


    message += ",";
    message += battery;
    message += ",";
    message += user;


    // message ??
    Serial.println(message);
    stringCharacteristic->setValue(message.c_str());


    isCheckCodeAvailableRunning = false; // ??? ??? ???? ?????.
}


void setup()
{
    Serial.begin(115200);
    while (!Serial)
        ;


    pinMode(IN1, OUTPUT);
    pinMode(IN2, OUTPUT);
    pinMode(IN3, OUTPUT);
    pinMode(IN4, OUTPUT);


    stepper.setMaxSpeed(500);
    stepper.setAcceleration(200);


    nfc.begin();
    uint32_t versiondata = nfc.getFirmwareVersion();
    if (!versiondata) {
      Serial.print("PN53x ??? ?? ? ????");
      while (1);
    }


    nfc.SAMConfig();
    Serial.println("NFC ??? ???? ?...");
    
    BLEDevice::init("SEOLO LOCK 1");


    pServer = BLEDevice::createServer();
    BLEService *pService = pServer->createService(BLEUUID(SERVICE_UUID));


    pCharacteristic = pService->createCharacteristic(
        BLEUUID(CHARACTERISTIC_UUID),
        BLECharacteristic::PROPERTY_READ |
            BLECharacteristic::PROPERTY_WRITE |
            BLECharacteristic::PROPERTY_NOTIFY);


    pCharacteristic->setCallbacks(new MyCallbacks());


    pService->start();


    BLEAdvertising *pAdvertising = pServer->getAdvertising();
    pAdvertising->start();


    Serial.println("SEOLO LOCK 1");


    // stringCharacteristic, battery ???
    stringCharacteristic = pCharacteristic;
    battery = 0;


    // ?? ? ?? ?? ??? ??? ??
    pServer->setCallbacks(&serverCallbacks);
    
    preferences.begin("nfc-data", false);
    uidStored = preferences.getBool("uidStored", false);
    lockState = preferences.getBool("lockState", false);
    savedToken = preferences.getString("savedToken", "");
    savedMachine = preferences.getString("savedMachine", "");
    if (uidStored) {
        size_t len = preferences.getBytesLength("storedUID");
        if (len == sizeof(storedUID)) {
            preferences.getBytes("storedUID", storedUID, len);
        }
    }
}


void loop()
{
    // ??? ?? ??
    int adcValue = analogRead(analogPin);
    float batteryVoltage = (adcValue * referenceVoltage / resolution) * voltageDivider;
    battery = mapBatteryVoltageToPercentage(batteryVoltage);


    // ??? ?? ??? ?? ?? ??? ?????.
    // ?? ?? ??? ???? ?? ?? ?? ? ??????.
    // if (digitalRead(EXIT_PIN) == HIGH || battery <= 10) {
    //     // ?? ?? ? ?? ?? ??
    //     preferences.putString("savedToken", savedToken);
    //     preferences.putString("savedMachine", savedMachine);
    //     preferences.putBool("uidStored", uidStored);
    //     preferences.putBool("lockState", lockState);
    //     preferences.putBytes("storedUID", storedUID, sizeof(storedUID));
    //     preferences.end(); // Preferences ?? ??
    //     delay(100); // ?? ??
    //     ESP.restart(); // ???
    // }


    readNFCAndBattery(); // NFC? ??? ??? ?????.
    while (stepper.distanceToGo() != 0) {
        stepper.run();
    }
    disableMotor();  // ?? ?? ?? ? ????


    delay(5000); // 5? ???
}


void disableMotor() {
  // ?? ?? LOW? ???? ??? ????
    digitalWrite(IN1, LOW);
    digitalWrite(IN2, LOW);
    digitalWrite(IN3, LOW);
    digitalWrite(IN4, LOW);
}


void readNFCAndBattery() {
  uint8_t success;
  uint8_t uid[7];
  uint8_t uidLength;


  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);


  // ??? ?? ??
  int adcValue = analogRead(analogPin);
  float batteryVoltage = (adcValue * referenceVoltage / resolution) * voltageDivider;
  int battery = mapBatteryVoltageToPercentage(batteryVoltage);
  Serial.print("???: ");
  Serial.print(batteryVoltage);
  Serial.print(" V - ");
  Serial.print(battery);
  Serial.println("%");


  if (success) {
    Serial.print("UID ??: "); Serial.print(uidLength, DEC); Serial.print(" ??? /");
    Serial.print(" UID ?:");
    for (uint8_t i = 0; i < uidLength; i++) {
        Serial.print(" 0x"); Serial.print(uid[i], HEX);
    }
    Serial.println("");


    handleNFC(uid, uidLength);
  }
}


// NFC? ?? ??
void handleNFC(uint8_t *uid, uint8_t uidLength) {
  if (lockState) {
      if (memcmp(masterKey, uid, 4) == 0) {
          Serial.println("??? ?/ ???. ?? ??.");
          clearStoredUID();
          savedToken = "";
          savedMachine = "";
          stepper.moveTo(-700);
      } else {
          Serial.println("???.");
      }
  } else {
      if (!uidStored) {
          memcpy(storedUID, uid, uidLength);
          uidStored = true;
          preferences.putBool("uidStored", uidStored);
          preferences.putBytes("storedUID", storedUID, uidLength);
          lockState = true;
          preferences.putBool("lockState", lockState);
          Serial.println("??. ?? ?? ???.");
          stepper.moveTo(700);
      }
  }
}


// BATTERY ??
int mapBatteryVoltageToPercentage(float voltage) {
    if (voltage >= 4.09) return 100;
    if (voltage >= 4.0) return 95;
    if (voltage >= 3.9) return 90;
    if (voltage >= 3.8) return 85;
    if (voltage >= 3.7) return 75;
    if (voltage >= 3.6) return 65;
    if (voltage >= 3.5) return 55;
    if (voltage >= 3.4) return 45;
    if (voltage >= 3.3) return 35;
    if (voltage >= 3.2) return 25;
    if (voltage >= 3.1) return 15;
    if (voltage >= 3.0) return 5;
    return 0;
}


// UID ??
void clearStoredUID() {
    memset(storedUID, 0, sizeof(storedUID));
    uidStored = false;
    lockState = false;
    preferences.putBool("uidStored", uidStored);
    preferences.putBool("lockState", lockState);
    preferences.remove("storedUID");
}

