import com.sensor.Sensor

// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class SensorTest extends munit.FunSuite {
  test("can identify CSV files in specified directory") {
    val sensors = new Sensor()

    var dir = "/Users/a/Documents/test/sensor/src/main/scala/test_files"
    var expected = 2
    var obtained = sensors.numOfProcessedFiles(dir)
    assertEquals(obtained, expected)
  }

  test("return zero if no CSV file is found") {
    val sensors = new Sensor()

    var dir = "/Users/a/Documents/test/sensor/src/main/scala"
    var expected = 0
    var obtained = sensors.numOfProcessedFiles(dir)
    assertEquals(obtained, expected)
  }

  test("return zero if no CSV file is found to process") {
    val sensors = new Sensor()

    var dir = "/Users/a/Documents/test/sensor/src/main/scala/test_files"

    var expected = 0
    var obtained =  sensors.numOfProcessedMeasurements()
    assertEquals(obtained, expected)
  }

  test("return total valid sensor measurements taken") {
    val sensors = new Sensor()

    var dir = "/Users/a/Documents/test/sensor/src/main/scala/test_files"
    sensors.numOfProcessedFiles(dir)

    var expected = 7
    var obtained =  sensors.numOfProcessedMeasurements()
    assertEquals(obtained, expected)
  }

  test("return total failed sensor measurements taken") {
    val sensors = new Sensor()

    var dir = "/Users/a/Documents/test/sensor/src/main/scala/test_files"
    sensors.numOfProcessedFiles(dir)
    sensors.numOfProcessedMeasurements()

    var expected = 2
    var obtained =  sensors.numOfFailedMeasurements()
    assertEquals(obtained, expected)
  }
}
