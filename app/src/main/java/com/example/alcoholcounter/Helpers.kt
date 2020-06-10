package com.example.alcoholcounter

import android.location.Location

// Tohle muze byt object misto class
class Helpers {
    companion object {
        fun stringFromLocation(location: Pair<Double, Double>): String? {
            return "[" + Location.convert(location.first, Location.FORMAT_DEGREES) +
                    ", " +
                    Location.convert(location.second, Location.FORMAT_DEGREES) + "]"
        }

    }
}