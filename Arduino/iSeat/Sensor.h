#pragma once

// library for the HX711 amplifier used to read load cell
#include <HX711_ADC.h>

// macros for arduion pins for sensor (also the LED pin)
#define SENSOR 4
#define CLK 5
#define LED LED_BUILTIN

// macros for setup
#define CALIBRATION_VALUE   696.0 // calibration value for load cell
#define STABILIZATION_TIME  2000  // time in milliseconds to stabilize sensor before reading

// function prototypes, definitions in 'Sensor.cpp'
void setupLoadCell();
bool sensorState(bool current);
