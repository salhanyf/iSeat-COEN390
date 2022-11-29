/*
  StateConnected.cpp
  COEN390 Team D
  Operating State of iSeat device, sends seat updates when assigned to a room.
*/
#include "States.h"

int cnt = 0;  // counter for loop
bool seatStatus = true, lastSeatStatus = true; // save changes in seat status

// The state function for CONNECTED
// Arduino is connected to WiFi and Firebase, operates as normal iSeat device
// Sends updates to Firebase if assigned to a room
// Blinks LED when not assigned to any room  
void connected() {
  delay(SLEEP_DELAY_MS); // small delay

  // check if connection lost, if so go back to UNCONNECTED state
  if (WiFi.status() != WL_CONNECTED) {
    WiFi.disconnect();  // disconnect wifi to be safe
    WiFi.end();         // end wifi to be safe
    state = UNCONNECTED;
    return;
  }

  // effective counter delay is CHECK_ROOM_CNT * SLEEP_DELAY_MS,
  // uC will check Firebase if assigned to a Room, and also update its WiFi strength in Firebase
  if (++cnt >= CHECK_ROOM_CNT) {
    updateRoom();
    updateWiFiStrength();
    cnt = 0;
  }

  // if sensor has room assigned to it, send status updates to Firebase
  if (hasRoom) updateSeatStatus();
  // else just blink LED until a room is assigned to it
  else blinkLed();
}

// checks Firebase and updates the hasRoom flag when assigned to a room
void updateRoom() {
  Serial.println("Checking if sensor is assigned to room...");
  // check Firebase Sensor node for its assigned roomKey
  // if TRUE, data access has succeeded
  // if FALSE, there was an error
  if (Firebase.getString(fbdo, "sensors/" + _mac + "/roomKey")) {
    // if roomID not zero, update hasRoom
    if (fbdo.stringData() != "") {
      Serial.println("roomKey: " + fbdo.stringData());
      hasRoom = true;
    }
    // else roomID = 0, update hasRoom
    else {
      Serial.println("No room.");
      hasRoom = false;
    }
  } else {
    // error occured during get from Firebase
    Serial.println("ERROR: " + fbdo.errorReason());
  }
  fbdo.clear();
}

// checks the iSeat devices WiFi connection strength and uploads to Firebase
void updateWiFiStrength() {
  // get WiFi strength from WiFi module
  String strength = getWiFiStrength();
  // upload the wifi strength to Firebase 
  Firebase.setString(fbdo, "sensors/" + _mac + "/wifiStatus", strength);
  // check if WiFi Status is weak
  if ((strength == "Weak") || (strength == "Very Weak"))
    Serial.println("WiFi Signal " + strength + "...");
  fbdo.clear();
}

// get WiFi strength from WiFi module
String getWiFiStrength() {
  // get wifi strength
  long rssi = WiFi.RSSI();
  // return corresponding strength string
  if (rssi > -70)       return "Strong";
  else if (rssi > -80)  return "Medium";
  else if (rssi > -90)  return "Weak";
  else                  return "Very Weak";
}

// updates the iSeat device node on Firebase
void updateSeatStatus() {
  // get status and print
  seatStatus = sensorState(seatStatus);
  Serial.print("Seat status: ");
  Serial.println(seatStatus ? "Open" : "Taken");

  // if status changed from last time update Firebase sensor entry
  if (seatStatus != lastSeatStatus) {
    digitalWrite(LED, seatStatus);  // show seat status on LED
    Firebase.setBool(fbdo, "sensors/" + _mac + "/status", seatStatus);  // upload to Firebase
    Serial.println("Status sent to Firebase: " + String(seatStatus)); // print
    fbdo.clear();
    lastSeatStatus = seatStatus;  // save last status
  }
}

// blinks led at consistant rate
void blinkLed() {
  static unsigned char tog = 0;
  digitalWrite(LED, (tog++) & 0x02);
}
