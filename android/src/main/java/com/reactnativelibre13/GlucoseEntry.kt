package com.reactnativelibre13

import kotlin.math.roundToInt

val GLUCOSE_ERROR = 21.1;
val GLUCOSE_UNIT = 0.113;

data class SensorChunk(val value: Int, val status: Int, val history: Boolean, val rest: Int) {
  constructor(b : ByteArray, history : Boolean) :
    this(RawParser.bin2int(b[1], b[0]),
      RawParser.byte2uns(b[2]),
      history,
      RawParser.bin2int(b[3], b[4], b[5]))
}

data class GlucoseReading(val value: Int, val utcTimeStamp: Long, val sensorId: Long, val status: Int, val history: Boolean, val rest: Int) {
  constructor(s: SensorChunk, utcTimeStamp: Long, sensorId: Long):
    this((((s.value/10) * GLUCOSE_UNIT) - GLUCOSE_ERROR).roundToInt(), utcTimeStamp, sensorId, s.status, s.history, s.rest)
}
