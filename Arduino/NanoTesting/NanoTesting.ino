#include <Firebase_Arduino_WiFiNINA.h>

// ***** CHANGE FOR YOUR FIREBASE *****
#define URL     "test-1ee43-default-rtdb.firebaseio.com"  // Firebase Website -> Build -> Realtime Database -> URL
#define API_KEY "AIzaSyAcNLVUsicx0dKUT__6ojBJ8P8N-HoTrmQ" // Firebase Website -> Project Settings -> Web API Key

// ***** CHANGE FOR YOUR NETWORK *****
#define SSID  "BELL325" 
#define PASS  "4217F6E9"

//Define Firebase data object
FirebaseData fbdo;
String mac;

// ***** CHANGE FUNCTION IMPLEMENTATION (for other sensors) *****
bool sensorState() {
  // for testing with PB switch:
  return !digitalRead(2); // button is default 0, and 1 when pushed
}

String getMac() {
  byte arr[WL_MAC_ADDR_LENGTH];
  String s = "";
  WiFi.macAddress(arr);
  for (int i = WL_MAC_ADDR_LENGTH - 1; i >= 0; i--)
    s += String(arr[i]) + (i > 0 ? ":" : "");
  return s;
}

void setup() {
  pinMode(2, INPUT);    // when using active-HIGH sensor/switch on D2 pin
  pinMode(13, OUTPUT);  // on-board LED pin D13
  
  Serial.begin(115200); // start serial communications
  delay(500);

  // connect to network
  Serial.print("\nConnecting to Wifi...");
  WiFi.begin(SSID, PASS);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(200);
  }
  
  // Print IP when connected
  Serial.print(" Connected.\nIP: ");
  Serial.println(WiFi.localIP());

  // save and print mac address
  mac = getMac();
  Serial.println("MAC: " + mac);

  //Provide the autntication data for firebase
  Firebase.begin(URL, API_KEY, SSID, PASS);
  Firebase.reconnectWiFi(true);
}

// seat status
bool status = true, lastStatus = true;

void loop() {
  delay(100); // small delay

  status = sensorState();  // get status according to sensor used

  digitalWrite(13, status); // show status on LED (ON: open-status=1, OFF: taken-status=0)

  // if status changed from last time update Firebase sensor entry
  if (status != lastStatus) {
    Firebase.setBool(fbdo, "sensors/" + mac + "/status", status);
    Serial.println("Status sent to Firebase: " + String(status));
    lastStatus = status;
  }
}
