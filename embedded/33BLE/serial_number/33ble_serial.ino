#include <ArduinoBLE.h>

void setup()
{
    Serial.begin(9600);
    while (!Serial)
        ;
}

void loop()
{
    Serial.print("Device ID 0: ");
    Serial.println(NRF_FICR->DEVICEID[0], HEX);
    Serial.print("Device ID 1: ");
    Serial.println(NRF_FICR->DEVICEID[1], HEX);

    Serial.print("Device Address 0: ");
    Serial.println(NRF_FICR->DEVICEADDR[0], HEX);
    Serial.print("Device Address 1: ");
    Serial.println(NRF_FICR->DEVICEADDR[1], HEX);

    delay(5000);
}

// #define PN532_SCK  (13)
// #define PN532_MOSI (11)
// #define PN532_SS   (10)
// #define PN532_MISO (12)
// #define RST_PIN    (9)
