#include "lib.h"
#include <HX711_ADC.h>
//HX711 constructor:
HX711_ADC LoadCell(4, 5);

// ***** Change macros SSID and PASS in lib.h for your network. *****
// ***** Change macros URL and API_KEY in lib.h for your firebase project *****

// ***** SEE DEFINES IN lib.h TO SELECT WHICH CODE SEGMENT WILL BE COMPILED: *****
inline bool sensorState(bool current) {
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

  // Arduino Example 1x load cell with HX711
  // Variables
  bool newDataReady = 0;

  // check for new data/start next conversion:
  if (LoadCell.update())
    newDataReady = true;

  // get smoothed value from the dataset:
  if (newDataReady) {
    float i = LoadCell.getData();  // Contains Raw data of load cell!!!

    // If load cell output (i) is greater than X, seat is taken else seat is open
    if (i > 1000) {
      Serial.print("Seat is taken, Current Pressure: ");
      Serial.println(i);
      return false;
    } else {
      Serial.print("Seat is open, Current Pressure: ");
      Serial.println(i);
      return true;
    }
  }

  else return current;
#endif
}

// Declared Variables
FirebaseData fbdo;  // Define Firebase data object
String mac = "";  // MAC address of device
bool hasRoom = false;
//pins:
const int HX711_dout = 4; //mcu > HX711 dout pin
const int HX711_sck = 5; //mcu > HX711 sck pin

void loadCellSetup() {
  // check if load cell is calibrated, else calibrate it (Added by Shahin 29/10/2022)
  LoadCell.begin();

  float calibrationValue = 696.0;          //calibration value for load cell
  unsigned long stabilizationTime = 2000;  //time in milliseconds to stabilize sensor before reading
  boolean _tare = true;                    //set to true to tare before reading

  LoadCell.start(stabilizationTime, _tare);

  if (LoadCell.getTareStatus()) {
    Serial.println("Tare complete");
    while(1);
  }
  else{
    LoadCell.setCalFactor(calibrationValue); //user set calibration value (float)
    Serial.println("Calibration complete");
  }
}

String WiFiStatus() {
  //An RSSI closer to 0 is stronger, and closer to –100 is weaker.
  //For best performance, you want your RSSI to be as high as possible. A useful rule of thumb is that if the RSSI is less than –70 dBm
  //you are unlikely to have good performance over Wi-Fi for bandwidth intensive tasks.
  long rssi = WiFi.RSSI();
  // Serial.print("Signal strength (RSSI): " + String(rssi) + " dBm\n");

  if (rssi > -70) {
    return "Strong";
  } else if (rssi > -80) {
    return "Medium";
  } else if (rssi > -90) {
    return "Weak";
  } else {
    return "Very Weak";
  }
}

void setup() {

  Serial.begin(57600);  // start serial communications
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
  if (Firebase.getString(fbdo, "sensors/" + mac + "/" + ROOM_KEY_STR)) {
    Serial.println("Success.\n" + fbdo.dataPath() + " : " + String(fbdo.stringData()));  // print success
    if (fbdo.stringData() != "")
      hasRoom = true;
  } else {
    Serial.println("\nError, " + fbdo.errorReason() + ": " + fbdo.dataPath());  // print error
    // add roomID="" entry to sensor if non-existant
    if (fbdo.errorReason() == "path not exist") {
      Serial.print("Adding " + ROOM_KEY_STR + " \'0\' to Firebase... ");
      Firebase.setString(fbdo, "sensors/" + mac + "/" + ROOM_KEY_STR, "");
      Firebase.setBool(fbdo, "sensors/" + mac + "/" + STATUS_STR, true);
      Serial.println("Done.");
    }
    fbdo.clear();
  }

#ifdef LOAD_CELL

  loadCellSetup();

#endif
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
    if (Firebase.getString(fbdo, "sensors/" + mac + "/" + ROOM_KEY_STR)) {
      // if roomID not zero, update hasRoom
      if (fbdo.stringData() != "") {
        Serial.println(ROOM_KEY_STR + ": " + fbdo.stringData());
        hasRoom = true;
      }
        // else roomID = 0, update hasRoom
      else {
        Serial.println("No room.");
        hasRoom = false;
      }
    } else {
      // error occured during get from Firebase
      Serial.println("\nError, " + fbdo.errorReason());
    }
    fbdo.clear();
    cnt = 0;
  }

  // if sensor has room assigned to it, send status updates to Firebase
  if (hasRoom) {
    Serial.print("Updating status and LED from sensor state... ");
    status = sensorState(status);  // get status according to sensor used
    Serial.println("Done.");

    // if status changed from last time update Firebase sensor entry
    if (status != lastStatus) {
      digitalWrite(LED, status);  // show seat status on LED
      Firebase.setBool(fbdo, "sensors/" + mac + "/" + STATUS_STR, status);
      Serial.println("Status sent to Firebase: " + String(status));
      fbdo.clear();
      lastStatus = status;
    }
  } else {
    static unsigned char tog = 0;
    digitalWrite(LED, (tog++) & 0x02);
  }

  // display Wifi Status in Firebase
  Firebase.setString(fbdo, "sensors/" + mac + "/" + WIFI_STR, WiFiStatus());
  // check if WiFi Status is weak, if so, reconnect
  if (WiFiStatus() == "Weak" || WiFiStatus() == "Very Weak") {
    Serial.println("WiFi Signal Weak...");
  }
}