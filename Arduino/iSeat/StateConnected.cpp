#include "States.h"

int cnt = 0;  // counter for loops
bool seatStatus = true, lastSeatStatus = true;

void connected() {
  delay(SLEEP_DELAY_MS); // small delay

  if (WiFi.status() != WL_CONNECTED) {
    WiFi.end();
    state = UNCONNECTED;
    return;
  }

  // effective counter delay is CHECK_ROOM_CNT * SLEEP_DELAY_MS,
  // uC will check Firebase if assigned to a Room
  if (++cnt >= CHECK_ROOM_CNT) {
    Serial.println("Checking if sensor is assigned to room...");
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
    cnt = 0;
  }

  // if sensor has room assigned to it, send status updates to Firebase
  if (hasRoom) {
    seatStatus = sensorState(seatStatus);  // get status according to sensor used
    Serial.print("Seat status: ");
    Serial.println(seatStatus ? "Open" : "Taken");

    // if status changed from last time update Firebase sensor entry
    if (seatStatus != lastSeatStatus) {
      digitalWrite(LED, seatStatus);  // show seat status on LED
      Firebase.setBool(fbdo, "sensors/" + _mac + "/status", seatStatus);
      Serial.println("Status sent to Firebase: " + String(seatStatus));
      fbdo.clear();
      lastSeatStatus = seatStatus;
    }
  } else {
    static unsigned char tog = 0;
    digitalWrite(LED, (tog++) & 0x02);
  }

  // display Wifi Status in Firebase
  Firebase.setString(fbdo, "sensors/" + _mac + "/wifiStatus", WiFiStatus());
  // check if WiFi Status is weak, if so, reconnect
  if ((WiFiStatus() == "Weak") || (WiFiStatus() == "Very Weak"))
    Serial.println("WiFi Signal Weak...");
  fbdo.clear();
}

String WiFiStatus() {
  long rssi = WiFi.RSSI();
  // Serial.print("Signal strength (RSSI): " + String(rssi) + " dBm\n");
  if (rssi > -70)       return "Strong";
  else if (rssi > -80)  return "Medium";
  else if (rssi > -90)  return "Weak";
  else                  return "Very Weak";
}
