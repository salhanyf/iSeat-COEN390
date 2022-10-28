#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

// SENSOR CONTROLLER BEHAVIOUR:
//  1. Connect to WiFi
//  2. Send "HELLO" to Firebase with MAC address (unique)
//  3. If already registered with room, start sending status updates (open/taken). GOTO: 5.
//  4. Else not registered, wait for APP to assign it a room. GOTO: 3.
//  5. Loop:
//  6.    If seat status changes, send update to Firebase (seat open/taken).
//  7.    If APP removes sensor from room, stop sending updates. GOTO: 3.
//  8.    GOTO: 5.

// CHANGE THESE VALUES DEPENDING ON YOUR SETUP
const char* SSID = "BELL325"; // CHANGE SSID TO YOUR NETWORK NAME
const char* PASS = "4217F6E9";  // CHANGE PASSWORD TO YOUR NETWORK PASSWORD
const char* SERVER = "http://192.168.2.93:8080";  // CHANGE TO LISTENING SERVER'S IP ADDRESS:PORT

const float VCC = 3.316;    // measured Vcc voltage used
const float RES = 975000.0; // measure Resistor value used

const int ADC_TRESH = 800;  // ADC code below => seat open, ADC code above => seat taken
const bool OPEN = true;     // seat status open
const bool TAKEN = false;   // seat status taken

// converts ADC code into corresponding resistance value, force (weight) and print
void printSensorReadings(int adcCode) {
  float sensV = adcCode * VCC / 1023.0;   // sensor voltage
  float sensR = RES * (VCC / sensV - 1.0);
  float sensG = 1.0 / sensR;
  float force;

  if (sensR <= 600) force = (sensG - 0.00075) / 0.00000032639;
  else              force = sensG / 0.000000642857;

  Serial.println("ADC: " + String(adcCode) + "\tResistance: " + String(sensR) + " ohms\tForce: " + String(force) + " g");
}

void sendStatusToServer(bool status) {
  if (WiFi.status() != WL_CONNECTED)
    return;

    WiFiClient client;
    HTTPClient http;
    String serverName = SERVER + String(status ? "/seatOpen" : "/seatTaken");
    
    Serial.println("HTTP GET to server: " + serverName);
    http.begin(client, serverName.c_str());
    int responseCode = http.GET();

    if (responseCode > 0) Serial.println("HTTP Response Code: " + String(responseCode) + "\nMessage: " + http.getString());
    else                  Serial.println("Error Code: " + String(responseCode));

    http.end();
}

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(A0, INPUT);
  pinMode(D4, OUTPUT);

  Serial.print("\n\nConnecting to wifi");
  WiFi.begin(SSID, PASS);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  String ipAddr = WiFi.localIP().toString();
  String macAddr = WiFi.macAddress();
  Serial.printf(" Connected.\nIP: %s\tMAC: %s\n", ipAddr.c_str(), macAddr.c_str());
}

bool status = true, lastStatus = true;

void loop() {
  // put your main code here, to run repeatedly:
  delay(100);

  int adcCode = analogRead(A0);
  printSensorReadings(adcCode);

  status = adcCode < ADC_TRESH ? OPEN : TAKEN;
  if (status != lastStatus) {
    sendStatusToServer(status);
    digitalWrite(D4, status);
    lastStatus = status;
  }
}
