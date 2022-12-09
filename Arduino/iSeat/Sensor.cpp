/*
  Sensor.cpp
  COEN390 Team D
  Sensor related functions, including pressure-sensor Load Cell with HX711 amplifier chip.
  Check Sensor.h header for LOAD_CELL macro, when defined the Arduino circuit uses the LOAD CELL.
  When not defined, there is a Film sensor / test push button being used with the Arduino circuit and code is simpler.
*/
#include "Sensor.h"


// when LOAD_CELL defined, a Load Cell sensor with amplifier (HX_711) is used in iSeat circuit
// when LOAD_CELL undefined, a simpler Film sensor or test Push button iSeat circuit is used
// #ifdef:  an LoadCell (amplifier) object/setup function are defined, as well as one version of sensorState()
// #else:   all that is needed is a second simpler version of sensorState()
#ifdef LOAD_CELL
// LOAD_CELL is defined

  // Load Cell object, use specified pins
  HX711_ADC loadCell(SENSOR, CLK);

  // load cell setup function
  // sets the stabilization time and calibration factor,
  // performs a tare, and starts the conversions
  void setupLoadCell() {
    loadCell.begin();
    Serial.println("Starting Load Cell with tare...");
    loadCell.start(STABILIZATION_TIME, true); // set to true to tare before reading
    Serial.println("Setting Load Cell calibration value...");
    loadCell.setCalFactor(CALIBRATION_VALUE); // set calibration value (float)
    Serial.println("Calibration complete");
  }

  // read the Load cell sensor value and return the seat status
  // return true: Seat open
  // return false: Seat taken
  bool sensorState(bool current) {
    // get smoothed value from the dataset if updated value available
    if (loadCell.update()) {
      float data = loadCell.getData();  // Contains Raw data of load cell!!!
      // If load cell output (data) is greater than X, seat is taken else seat is open
      if (data > THRESHOLD) {
        Serial.print("Seat is taken, Current Pressure: ");
        Serial.println(data);
        // Firebase.setFloat(fbdo, "sensors/" + _mac + "/pressure", data);
        return false;
      }
      else {
        Serial.print("Seat is open, Current Pressure: ");
        Serial.println(data);
        // Firebase.setFloat(fbdo, "sensors/" + _mac + "/pressure", data);
        return true;
      }
    }
    // just return current value if conversion not ready
    return current;
  }

#else
// LOAD_CELL is NOT defined

  // read pushbutton or Film Sensor and return the seat status
  // Both are connected to SENSOR pin with pullup resistors, active LOW
  // Return the value of digitalRead() of pin
  // true: Seat open
  // false: Seat taken
  bool sensorState(bool current) {
    return digitalRead(SENSOR);
  }

#endif