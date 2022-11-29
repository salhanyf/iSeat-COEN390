/*
  iSeat.ino
  COEN390 Team D
  Main code for iSeat device.  
*/

////////////////////////////////////////////////////////////////////////////////
//                                  WARNING:                                  //
//                                                                            //
//  Make sure to comment/uncomment macro LOAD_CELL in "Sensor.h" depending on //
//  the current circuit being used:                                           //
//                                                                            //
//  - Load Cell with Amplifier: Uncomment LOAD_CELL,                          //
//  - Film Sensor / Pushbutton: Comment out LOAD_CELL                         //
//                                                                            //
#include "Sensor.h" // sensor functions/macros (LOAD_CELL macro in here)      //
////////////////////////////////////////////////////////////////////////////////

#include "States.h" // state macros/functions/data

// global variables shared by 1+ state
State state;                // state machine state
String _ssid, _pass, _mac;  // network SSID/password and MAC address
FirebaseData fbdo;          // Firebase data object
bool hasRoom;               // flag for having a room assigned to sensor

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
  // _ssid = "MYNETWORKNAME";
  // _pass = "myPassword123";
}

// run state machine in loop
void loop() {

  if (state == UNCONNECTED)     // Arduino function as Access Point. Serves web page to
    unconnected();              // enter a Network SSID & password to connect Arduino to

  else if (state == CONNECTING) // Arduino tries to connect to given Network.
    connecting();               // Closes Access Point, setup WiFi and Firebase

  else if (state == CONNECTED)  // Arduino functions as iSeat sensor. Send updates
    connected();                // on seat status to Firebase if room assigned to it.

}
