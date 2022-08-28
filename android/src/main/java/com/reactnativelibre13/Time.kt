package com.reactnativelibre13

class Time {
  companion object {
    val DURATION_MINUTES = 20160
    var MINUTES_DAY = 1440.0
    fun now() : Long = System.currentTimeMillis()

    fun timeLeft(timeStamp: Int) : Double {
      val left = DURATION_MINUTES - timeStamp
      if (left < 0) {
        return 0
      }

      return left/MINUTES_DAY
    }
  }
}

