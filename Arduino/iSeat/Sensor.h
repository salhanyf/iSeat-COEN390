/*
  Sensor.h
  COEN390 Team D
  Header file for Sensor.h. Contains libraries, macros, and function prototypes.
  * COMMENT/UNCOOMENT the macro LOAD_CELL depending on iSeat Arduino circuit *
*/
#pragma once

// library for the HX711 amplifier used to read load cell
#include <HX711_ADC.h>

/********** UNCOMMENT/COMMENT FOLLOWING LINE IF LOAD CELL USED/UNUSED **********/

#define LOAD_CELL

/*******************************************************************************/

// macros for arduion pins for sensor (also the LED pin)
#define SENSOR 4
#define CLK 5
#define LED LED_BUILTIN

// macros for load cell setup
#define CALIBRATION_VALUE   696.0 // calibration value for load cell
#define STABILIZATION_TIME  2000  // time in milliseconds to stabilize sensor before reading

// macro for seat open/taken threshold value
#define THRESHOLD 1000

// function prototypes, definitions in 'Sensor.cpp'
void setupLoadCell();
bool sensorState(bool current);
