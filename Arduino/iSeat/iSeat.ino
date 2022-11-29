/*
  iSeat.ino : main code for iSeat tracker.
  COEN390 Team D
*/

// ***** UNCOMMENT/COMMENT FOLLOWING LINE IF LOAD CELL USED/UNUSED
// #define LOAD_CELL

// libraries
#include "Sensor.h"
#include "States.h"

// global variables shared by 1+ state
State state;
String _ssid, _pass, _mac;
FirebaseData fbdo;
bool hasRoom;

// setup Arduino
void setup() {
  Serial.begin(115200); // start serial connection
  while (!Serial);      // wait for connection to be open

  pinMode(SENSOR, INPUT); // set sensor pin input
  pinMode(LED, OUTPUT);   // set led pin output

#ifdef LOAD_CELL
  setupLoadCell();  // if LOAD_CELL is defined/used then set it up too
#endif

  // initialize state machine and variables
  state = UNCONNECTED;        // Start state: UNCONNECTED
  _ssid = _pass = _mac = "";  // clear all Network info strings
  hasRoom = false;            // flag for sensor assigned to room

  // set the following to immediately connect to a home Network for quick testing
  // state = CONNECTING;
  // _ssid = "BELL343";
  // _pass = "398f9kke9";
}

void loop() {

  // arduino function as Access Point. Serves web page to enter a Network SSID & password to connect Arduino to
  if (state == UNCONNECTED)
    unconnected();

  // Arduino tries to connect to given Network. Closes Access Point, WiFi Setup and Firebase Setup
  else if (state == CONNECTING)
    connecting();

  // Arduino functions as iSeat sensor
  else if (state == CONNECTED)
    connected();

}
