package com.sensor

import com.github.tototoshi.csv.*
import java.io.File

@main def main(arg: String): Unit = {

  if(arg == ""){
    throw Exception("argument not passeed!")
  }

  var dir = arg

  val sensors = new Sensor()
  
  val noOfProcessedFile=sensors.numOfProcessedFiles(dir)
  println("Num of processed files:" + noOfProcessedFile)
  
  val numOfProcessedMeasure=sensors.numOfProcessedMeasurements()
  println("Num of processed measurements: "+numOfProcessedMeasure)
  
  val numOfFailedMeasure=sensors.numOfFailedMeasurements()
  println("Num of failed measurements: "+numOfFailedMeasure)
  
  sensors.minAvgMaxHumidityPerSensor()
}