#pragma once

// libraries
#include <WiFiNINA.h>
#include <Firebase_Arduino_WiFiNINA.h>
#include "Sensor.h"

// state machine states
enum State{ UNCONNECTED, CONNECTING, CONNECTED };

// global variables shared by 1+ state
extern State state;
extern String _ssid, _pass, _mac;
extern FirebaseData fbdo;
extern bool hasRoom;

////////////////////////////////////////////////// Unconnected State

// macros used in state
#define ISEAT_SSID "iSeat"

// function prototypes for state, see 'StateUnconnected.cpp' for definitions
void unconnected();
void parseArgs(String req, String& ssid, String& pass);
void responseInput(WiFiClient& client, const String& ssid, const String& pass);
void responseConnecting(WiFiClient& client, const String& ssid, const String& pass);

////////////////////////////////////////////////// Connecting State

// macros used in state
#define TIMEOUT           10000
#define FIREBASE_URL      "test-1ee43-default-rtdb.firebaseio.com"  // Firebase Website -> Build -> Realtime Database -> URL
#define FIREBASE_API_KEY  "AIzaSyAcNLVUsicx0dKUT__6ojBJ8P8N-HoTrmQ" // Firebase Website -> Project Settings -> Web API Key

// function prototypes for state, see 'StateConnecting.cpp' for definitions
void connecting();
void setupFirebase();
String getMac();

////////////////////////////////////////////////// Connected State

// macros used in state
#define SLEEP_DELAY_MS      500
#define CHECK_ROOM_DELAY_S  4
#define CHECK_ROOM_CNT      ((CHECK_ROOM_DELAY_S * 1000) / SLEEP_DELAY_MS)

// function prototypes for state, see 'StateConnected.cpp' for definitions
void connected();
String WiFiStatus();

//////////////////////////////////////////////////
