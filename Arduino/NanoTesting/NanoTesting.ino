#include "lib.h"

// ***** Change macros SSID and PASS in lib.h for your network. *****
// ***** Change macros URL and API_KEY in lib.h for your firebase project *****

// ***** SEE DEFINES IN lib.h TO SELECT WHICH CODE SEGMENT WILL BE COMPILED: *****
inline bool sensorState() {
#ifdef PB_TEST_SENSOR
  // FOR TESTING WITH PB SWITCH:
  return digitalRead(PIN);
#endif

#ifdef FILM_SENSOR
  // FOR TESTING WITH FILM SENSOR:
#endif

#ifdef LOAD_CELL
  // FOR TESTING WITH LOAD CELL:
  // put sensor data aquisition algorithm here.
  // bool return value must be TRUE if seat is open, FALSE if seat is taken
#endif
}

FirebaseData fbdo;  // Define Firebase data object
String mac = "";  // MAC address of device
bool hasRoom = false;

void setup() {
  
  Serial.begin(9600); // start serial communications
  delay(2 * SLEEP_DELAY_MS);

  // setup GPIOs
  Serial.print("Setting up GPIOS... ");
  setupPins();
  Serial.println("Done.");

  // connect to network
  Serial.print("Connecting to Wifi...");
  setupWifi();
  Serial.println(" Done.");
  
  // Print IP and MAC
  Serial.print("IP: ");
  Serial.println(WiFi.localIP());
  mac = getMac();
  Serial.println("MAC: " + mac);

  //Provide the authentication data for firebase
  Serial.print("Authenticating Firebase... ");
  Firebase.begin(URL, API_KEY, SSID, PASS);
  Firebase.reconnectWiFi(true);
  Serial.println("Done.");

  // check if roomID for sensor is initialized in Firebase, else initialize it
  Serial.print("Checking if roomID entry exists for sensor... ");
  if (Firebase.getInt(fbdo, "sensors/" + mac + "/roomID")) {
    Serial.println("Success.\n" + fbdo.dataPath() + " : " + String(fbdo.intData()));  // print success
    if (fbdo.intData() != 0)
      hasRoom = true;
  }
  else {
    Serial.println("\nError, " + fbdo.errorReason() + ": " + fbdo.dataPath());  // print error
    
    // add roomID=0 entry to sensor if non-existant
    if (fbdo.errorReason() == "path not exist") {
      Serial.print("Adding roomID \'0\' to Firebase... ");
      Firebase.setInt(fbdo, "sensors/" + mac + "/roomID", 0);
      Serial.println("Done.");
    }
    fbdo.clear();
  }
}

void loop() {
  // seat status, last loop status flags
  static bool status = true, lastStatus = true;
  static int cnt = 0;  // counter for loops

  delay(SLEEP_DELAY_MS); // small delay

  // effective counter delay is CHECK_ROOM_CNT * SLEEP_DELAY_MS,
  // uC will check Firebase if assigned to a Room
  if (++cnt >= CHECK_ROOM_CNT) {
    Serial.print("Checking if sensor is assigned to room... ");
    if (Firebase.getInt(fbdo, "sensors/" + mac + "/roomID")) {
      // if roomID not zero, update hasRoom
      if (fbdo.intData() != 0) {
        Serial.println("roomID: " + String(fbdo.intData()));
        hasRoom = true;
      }
      // else roomID = 0, update hasRoom
      else {
        Serial.println("No room.");
        hasRoom = false;
      }
    }
    else {
      // error occured during get from Firebase
      Serial.println("\nError, " + fbdo.errorReason());
    }
    fbdo.clear();
    cnt = 0;
  }

  // if sensor has room assigned to it, send status updates to Firebase
  if (hasRoom) {
    Serial.print("Updating status and LED from sensor state... ");
    status = sensorState();     // get status according to sensor used
    digitalWrite(LED, status);  // show status on LED (ON: open-status=1, OFF: taken-status=0)
    Serial.println("Done.");

    // if status changed from last time update Firebase sensor entry
    if (status != lastStatus) {
      Firebase.setBool(fbdo, "sensors/" + mac + "/status", status);
      Serial.println("Status sent to Firebase: " + String(status));
      fbdo.clear();
      lastStatus = status;
    }
  }
  // blink LED when sensor has no room
  else {
    static int blink = 0;
    static bool toggle = false;

    if (++blink >= 2) {
      digitalWrite(LED, toggle ^= true);
      blink = 0;
    }
  }
}
