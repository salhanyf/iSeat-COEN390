#include "Sensor.h"

HX711_ADC loadCell(SENSOR, CLK);  // Load Cell object, use specified pins

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

// ifdef changes definition of sensorState() function if LOAD_CELL defined in 'iSeat.ino'
#ifdef LOAD_CELL

  // read the Load cell sensor value and return the seat status
  // return true: Seat open
  // return false: Seat taken
  bool sensorState(bool current) {
    // get smoothed value from the dataset if updated value available
    if (loadCell.update()) {
      float data = loadCell.getData();  // Contains Raw data of load cell!!!
      // If load cell output (i) is greater than X, seat is taken else seat is open
      if (data > 1000) {
        Serial.print("Seat is taken, Current Pressure: ");
        Serial.println(i);
        return false;
      }
      else {
        Serial.print("Seat is open, Current Pressure: ");
        Serial.println(i);
        return true;
      }
    }
    // just return current value if conversion not ready
    return current;
  }

#else

  // read pushbutton or Film Sensor and return the seat status
  // Both are connected to SENSOR pin with pullup resistors, active LOW
  // Return the value of digitalRead() of pin
  // true: Seat open
  // false: Seat taken 
  bool sensorState(bool current) {
    return digitalRead(SENSOR);
  }

#endif
