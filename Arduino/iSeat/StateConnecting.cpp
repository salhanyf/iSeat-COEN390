#include "States.h"

void connecting() {
  WiFi.disconnect();
  delay(500);
  
  Serial.print("Connecting to Wifi...");
  WiFi.begin(_ssid.c_str(), _pass.c_str());
  unsigned long timestamp = millis();
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(200);
    if (millis() - timestamp > TIMEOUT) {
      Serial.println("\nTimeout connecting to WiFi... Returning to state=UNCONNECTED");
      WiFi.end();
      state = UNCONNECTED;
      return;
    }
  }
  
  // Print IP and MAC
  Serial.print("Success!\nIP: ");
  Serial.println(WiFi.localIP());
  _mac = getMac();
  Serial.println("MAC: " + _mac);

  setupFirebase();

  state = CONNECTED;
}

void setupFirebase() {
  Serial.println("Connecting to Firebase...");
  Firebase.begin(FIREBASE_URL, FIREBASE_API_KEY, _ssid, _pass);
  Firebase.reconnectWiFi(true);
  Serial.println("Success!");

  Serial.println("Checking if roomKey entry exists for sensor...");
  if (Firebase.getString(fbdo, "sensors/" + _mac + "/roomKey")) {
    Serial.println("Success: " + fbdo.dataPath() + " == " + String(fbdo.stringData()));  // print success
    if (fbdo.stringData() != "")
      hasRoom = true;
  } else {
    Serial.println("ERROR " + fbdo.errorReason() + " : " + fbdo.dataPath());  // print error
    if (fbdo.errorReason() == "path not exist") {
      Serial.println("Adding new sensor to Firebase...");
      Firebase.setString(fbdo, "sensors/" + _mac + "/roomKey", "");  // new empty roomKey
      Firebase.setBool(fbdo, "sensors/" + _mac + "/status", true);   // new status is true (open)
      Serial.println("Done");
    }
    fbdo.clear();
  }
}

String getMac() {
  byte m[WL_MAC_ADDR_LENGTH];
  WiFi.macAddress(m);
  return String(m[5]) + ":" + String(m[4]) + ":" + String(m[3]) + ":" + String(m[2]) + ":" + String(m[1]) + ":" + String(m[0]);
}
