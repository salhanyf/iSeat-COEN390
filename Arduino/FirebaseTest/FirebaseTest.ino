#include <ESP8266WiFi.h>
#include <Firebase_ESP_Client.h>

// helpers for token and Real Time Database
#include "addons/TokenHelper.h"
#include "addons/RTDBHelper.h"

// WiFi network SSID and Password
#define SSID "BELL325"    // *** CHANGE TO THE ONE YOU ARE USING
#define PASS "4217F6E9"   // *** CHANGE TO THE ONE YOU ARE USING

// Firebase API key and URL.
#define API_KEY "AIzaSyAcNLVUsicx0dKUT__6ojBJ8P8N-HoTrmQ"         // Firebase Website -> Project Settings -> Web API Key
#define DB_URL "https://test-1ee43-default-rtdb.firebaseio.com/"  // Firebase Website -> Build -> Realtime Database -> URL

FirebaseData db;        // Firebase instance
FirebaseAuth auth;      // Firebase authentication data
FirebaseConfig config;  // Firebase config data

bool signUpOK = false;  // Firebase Connection went OK flag

void setup() {
  // setup uC pins
  pinMode(A0, INPUT);   // sensor pin (analog)
  pinMode(D4, OUTPUT);  // led pin (on if seat open, off if seat taken)
  
  // start serial connection
  Serial.begin(9600);

  // connect to SSID wifi network
  Serial.print("Connecting to WiFi");
  WiFi.begin(SSID, PASS);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(200);
  }

  // print IP and MAC addresses of device once connected
  Serial.println("Connected.\nIP: " + WiFi.localIP().toString() + "\nMAC: " + WiFi.macAddress());

  config.api_key = API_KEY;     // config Firebase API key
  config.database_url = DB_URL; // config Firebase URL

  // sign device in to firebase
  if (Firebase.signUp(&config, &auth, "", "")) {
    Serial.println("Firebase SignUp OK");
    signUpOK = true;
  }
  else
    Serial.printf("%s\n", config.signer.signupError.message.c_str());

  // idk what this does but need it
  config.token_status_callback = tokenStatusCallback;

  // start firebase object with config and auth
  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true); // try to reconnect if wifi lost
}

bool status = true, lastStatus = true; // track changes in sensor status

void loop() {
  delay(500); // small delay

  // if Firebase ready and available
  if (Firebase.ready() && signUpOK) {
    status = analogRead(A0) < 512;  // read sensor and set status accordingly

    // if status is changed from last status
    if (status != lastStatus) {

      //update sensor status in Firebase
      Firebase.RTDB.setBool(&db, "sensors/" + String(WiFi.macAddress()) + "/status", status);
      Serial.println("Status sent to Firebase: " + String(status));
      lastStatus = status;
    }
  }
}