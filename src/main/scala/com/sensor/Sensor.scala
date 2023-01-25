package com.sensor

import com.github.tototoshi.csv.*

import java.io.File
import scala.collection.mutable.ListBuffer


trait SensorStatic:
  def numOfProcessedFiles(dir: String): Int

  def numOfProcessedMeasurements(): Int

  def numOfFailedMeasurements(): Int

  def minAvgMaxHumidityPerSensor(): Unit


class Sensor extends SensorStatic :

  private var mean = ""
  private var fileList: List[String] = List()
  private var data: Map[String, ListBuffer[Int]] = Map()
  private var failedMesurment: Int = 0

  def numOfProcessedFiles(directory: String): Int = {
    val dir = new File(directory)
    if (dir.exists && dir.isDirectory) {
      fileList = dir.listFiles.filter(_.isFile).filter(_.toString().endsWith(".csv")).map(_.toString).toList
    }

    return fileList.size
  }

  def numOfProcessedMeasurements(): Int = {
    var totalMeasurment = 0
    for (d <- fileList) {
      try {
        val reader = CSVReader.open(new File(d)).allWithHeaders()
        totalMeasurment += reader.size
        for (a <- reader) {
          var sensorId: String = a("sensor-id")
          var humidity: String = a("humidity")

          if (!data.contains(sensorId)) {
            data += (sensorId -> ListBuffer())
          }
          if (humidity != "NaN") {
            data = data.updated(sensorId, data(sensorId).append(humidity.toInt))
          } else {
            failedMesurment += 1
          }
        }
      }
      catch {
        case e: Exception => e.printStackTrace
      }
    }

    return totalMeasurment
  }

  def numOfFailedMeasurements(): Int = {
    return failedMesurment
  }

  def minAvgMaxHumidityPerSensor(): Unit = {
    println("\nSensors with highest avg humidity:\n")

    println("sensor-id,min,avg,max")

    var result: List[(String, Any, Any, Any)] = List()
    var sensor: List[(String, Int, Int, Int)] = List()


      data.filter(_._2.size > 0).map{
        (k, v) => val avg = v.sum/v.size
            sensor = (k, v.min, avg, v.max) :: sensor
      }

    result = result ++ sensor.sortWith(_._3 > _._3)

    data.filter(_._2.size == 0).map{
      (k, v) => result = result :+ (k, "NaN","NaN","NaN")
    }

    result.map(a => println(s"${a._1},${a._2},${a._3},${a._4}"))
    }