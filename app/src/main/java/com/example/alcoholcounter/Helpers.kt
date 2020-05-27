package com.example.alcoholcounter

import android.location.Location

class Helpers {
    companion object {
        fun stringFromLocation(location: Pair<Double, Double>): String? {
            return "[" + Location.convert(location.first, Location.FORMAT_DEGREES) +
                    ", " +
                    Location.convert(location.second, Location.FORMAT_DEGREES) + "]"
        }

    }
}