/*
  StateConnecting.cpp
  COEN390 Team D
  Connecting State of iSeat device, tries to connect to a given Network and sets up Firebase.
*/
#include "States.h"

// The state function for CONNECTING
// Disconnects Access Point and tries to connect to the given SSID with the Password
// Performs the WiFi setup, Firebase setup, and a final check to see if the iSeat device is exist in Firebase
// returns to UNCONNECTED state if cant connect to the Network
// moves on to CONNECTED state if connection to Network is successful
void connecting() {
  // disconnect/end AP and wait a bit
  WiFi.disconnect();
  WiFi.end();
  delay(500);
  Serial.print("Connecting to Wifi...");
  WiFi.begin(_ssid.c_str(), _pass.c_str()); // connect to given SSID using Password
  unsigned long timestamp = millis();       // save timestamp
  while (WiFi.status() != WL_CONNECTED) {   // while not connected to WiFi
    delay(200);
    Serial.print(".");
    // check for timeout, user probably entered wrong SSID/pass
    if (millis() - timestamp > TIMEOUT) {
      Serial.println("\nTimeout connecting to WiFi... Returning to state=UNCONNECTED");
      // disconnect/end WiFi connection, and go back to UNCONNECTED state
      WiFi.disconnect();
      WiFi.end();
      state = UNCONNECTED;
      return;
    }
  }
  // Print IP and MAC when successfully connected
  Serial.print("Success!\nIP: ");
  Serial.println(WiFi.localIP());
  _mac = getMac();
  Serial.println("MAC: " + _mac);
  // setup the connection to Firebase and set state to CONNECTED
  setupFirebase();
  state = CONNECTED;
}

// get MAC address string from the Arduino WiFi module
String getMac() {
  byte m[WL_MAC_ADDR_LENGTH]; // array for each MAC byte
  WiFi.macAddress(m); // get the mac from WiFi module
  // return string version of MAC address
  return String(m[5]) + ":" + String(m[4]) + ":" + String(m[3]) + ":" + String(m[2]) + ":" + String(m[1]) + ":" + String(m[0]);
}

// setup the connection to Firebase
void setupFirebase() {
  Serial.println("Connecting to Firebase...");
  // use special URL and API key, with given SSID and Password
  Firebase.begin(FIREBASE_URL, FIREBASE_API_KEY, _ssid, _pass);
  Firebase.reconnectWiFi(true);
  Serial.println("Success!");
  // setup/check if sensor node exist on Firebase
  setupSensorNode();
}

// check and setup the iSeat sensor's node in Firebase
void setupSensorNode() {
  Serial.println("Checking if roomKey entry exists for sensor...");
  // try to get the roomKey assigned to sensor
  // if TRUE, Sensor node exists on Firebase
  // if FALSE, Sensor is new and sets up a Firebase node for itself
  if (Firebase.getString(fbdo, "sensors/" + _mac + "/roomKey")) {
    Serial.println("Success: " + fbdo.dataPath() + " == " + String(fbdo.stringData()));  // print success
    // change hasRoom to TRUE if assigned to a room (roomKey not empty)
    if (fbdo.stringData() != "")
      hasRoom = true;
  } else {
    Serial.println("ERROR " + fbdo.errorReason() + " : " + fbdo.dataPath());  // print error
    // create new node since this is first time the device connects
    if (fbdo.errorReason() == "path not exist") {
      Serial.println("Adding new sensor to Firebase...");
      Firebase.setString(fbdo, "sensors/" + _mac + "/roomKey", "");  // new empty roomKey
      Firebase.setBool(fbdo, "sensors/" + _mac + "/status", true);   // new status is true (open)
      Serial.println("Done");
    }
    fbdo.clear();
  }
}