#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266HTTPClient.h>

const char* SSID = "BELL325";
const char* PASS = "4217F6E9";
const char* SERVER = "http://192.168.2.93:8080";

const float VCC = 3.316;
const float RES = 975000.0;

const int ADC_TRESH = 800;
const bool OPEN = true;
const bool TAKEN = false;

void printSensorReadings(int adcCode) {
  float sensV = adcCode * VCC / 1023.0;
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

    if (responseCode > 0) {
      Serial.println("HTTP Response Code: " + String(responseCode));
      Serial.println("Message: " + http.getString());
    }
    else {
      Serial.println("Error Code: " + String(responseCode));
    }

    http.end();
}

void setup() {
  // put your setup code here, to run once:
  pinMode(A0, INPUT);
  Serial.begin(9600);

  Serial.print("Connecting to wifi");
  WiFi.begin(SSID, PASS);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.printf(" Connected. IP address: %d\n", WiFi.localIP());
}

unsigned long lastTime = 0;
int adcCode = 0;
bool status = true, lastStatus = true;

void loop() {
  // put your main code here, to run repeatedly:
  delay(100);
  unsigned long time = millis();

  if (time - lastTime > 1000) {
    printSensorReadings(adcCode = analogRead(A0));
    status = adcCode >= ADC_TRESH ? TAKEN : OPEN;
    if (status != lastStatus) {
      sendStatusToServer(status);
      lastStatus = status;
    }
    lastTime = millis();
  }
}

