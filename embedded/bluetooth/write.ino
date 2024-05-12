/*
Arduino NANO 33 IoT기준
write value가 utf-8일 때, 회사코드가 같으면 led를 켜고 연결을 유지하고, 다르면 led를 끄고 연결도 해제는 코드
*/

#include <ArduinoBLE.h>

#define AUTHENTICATION_CODE "SFY001KOR"

BLEService ledService("19B10000-E8F2-537E-4F6C-D104768A1214");                                                      // create service
BLECharacteristic stringCharacteristic("19B10001-E8F2-537E-4F6C-D104768A1214", BLERead | BLEWrite | BLENotify, 20); // create characteristic for string

const int ledPin = LED_BUILTIN; // pin to use for the LED

String receivedString = ""; // variable to store received string

void setup()
{
    Serial.begin(9600);
    while (!Serial)
        ;

    pinMode(ledPin, OUTPUT); // use the LED pin as an output

    // begin initialization
    if (!BLE.begin())
    {
        Serial.println("starting Bluetooth® Low Energy module failed!");
        while (1)
            ;
    }

    // set the local name peripheral advertises
    BLE.setLocalName("SSAFY LOCK 1");
    // set the UUID for the service this peripheral advertises
    BLE.setAdvertisedService(ledService);

    // add the characteristic to the service
    ledService.addCharacteristic(stringCharacteristic);

    // add service
    BLE.addService(ledService);

    // assign event handlers for connected, disconnected to peripheral
    BLE.setEventHandler(BLEConnected, blePeripheralConnectHandler);
    BLE.setEventHandler(BLEDisconnected, blePeripheralDisconnectHandler);

    // assign event handler for characteristic written
    stringCharacteristic.setEventHandler(BLEWritten, switchCharacteristicWritten);

    // start advertising
    BLE.advertise();

    Serial.println(("SEOLO"));
}

void loop()
{
    // poll for Bluetooth® Low Energy events
    BLE.poll();
}

void switchCharacteristicWritten(BLEDevice central, BLECharacteristic characteristic)
{
    // central wrote new value to characteristic, update LED
    Serial.print("Characteristic event, written: ");

    // Convert the data to a string
    const uint8_t *data = characteristic.value();
    int length = characteristic.valueLength();
    String receivedString;

    for (int i = 0; i < length; i++)
    {
        receivedString += (char)data[i];
    }

    Serial.println(receivedString);

    // Process received string here
    if (receivedString == AUTHENTICATION_CODE)
    {
        digitalWrite(ledPin, HIGH);
    }
    else
    {
        digitalWrite(ledPin, LOW);
        central.disconnect();
    }
}

void blePeripheralConnectHandler(BLEDevice central)
{
    // central connected event handler
    Serial.print("Connected event, central: ");
    Serial.println(central.address());
}

void blePeripheralDisconnectHandler(BLEDevice central)
{
    // central disconnected event handler
    Serial.print("Disconnected event, central: ");
    Serial.println(central.address());
}
