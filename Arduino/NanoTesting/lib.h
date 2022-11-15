#pragma once

#include <Firebase_Arduino_WiFiNINA.h>

// ***** CHANGE FOR YOUR FIREBASE *****
#define URL     "test-1ee43-default-rtdb.firebaseio.com"  // Firebase Website -> Build -> Realtime Database -> URL
#define API_KEY "AIzaSyAcNLVUsicx0dKUT__6ojBJ8P8N-HoTrmQ" // Firebase Website -> Project Settings -> Web API Key

#define ROOM_KEY_STR  String("roomKey")
#define STATUS_STR    String("status")
#define WIFI_STR    String("wifiStatus")

// ***** CHANGE FOR YOUR NETWORK *****
#define SSID  "BELL188"
#define PASS  "3FAD3755"  // DONT HACK ME PLZ (SHAHIN/SHAYAN)
//#define SSID  "BELL325"
//#define PASS  "4217F6E9"  // DONT HACK ME PLZ (SAMSON)

// ***** Uncomment sensor used for current HW setup *****
#define PB_TEST_SENSOR
// #define FILM_SENSOR
// #define LOAD_CELL

#define LED 13  // on-board LED on pin 13
#define PIN 4   // sensor input pin
#define CLK 5   // clock for Load Cell amplifier

// timing macros
#define SLEEP_DELAY_MS      500
#define CHECK_ROOM_DELAY_S  4
#define CHECK_ROOM_CNT      ((CHECK_ROOM_DELAY_S * 1000) / SLEEP_DELAY_MS)

#ifdef PB_TEST_SENSOR
// define ERROR if other sensor is defined too
#ifdef FILM_SENSOR
#define SENSOR_DEF_ERROR
#endif
#ifdef LOAD_CELL
#define SENSOR_DEF_ERROR
#endif
#endif

#ifdef FILM_SENSOR
// define ERROR if other sensor is defined too
  #ifdef PB_TEST_SENSOR
    #define SENSOR_DEF_ERROR
  #endif
  #ifdef LOAD_CELL
    #define SENSOR_DEF_ERROR
  #endif
#endif

#ifdef LOAD_CELL
// define ERROR if other sensor is defined too
  #ifdef PB_TEST_SENSOR
    #define SENSOR_DEF_ERROR
  #endif
  #ifdef FILM_SENSOR
    #define SENSOR_DEF_ERROR
  #endif
#endif

// if two+ sensor defines were uncommented
#ifdef SENSOR_DEF_ERROR
#error Please make sure only 1 sensor is defined in lib.h
#endif

// get mac address of WiFi module (unique)
inline String getMac() {
  byte m[WL_MAC_ADDR_LENGTH];
  WiFi.macAddress(m);
  return String(m[5]) + ":" + String(m[4]) + ":" + String(m[3]) + ":" + String(m[2]) + ":" + String(m[1]) + ":" + String(m[0]);
}

inline void setupPins() {
  pinMode(PIN, INPUT);  // setup pin for sensor input
  pinMode(CLK, OUTPUT);     // setup clock for Load Cell Amplifier
  pinMode(LED, OUTPUT);     // on-board LED pin D13
  digitalWrite(CLK, HIGH);
  digitalWrite(LED, LOW);
}

inline void setupWifi() {
  WiFi.begin(SSID, PASS);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(200);
  }
}