package com.example.alcoholcounter

import android.location.Location

class Helpers {
    companion object {
        fun stringFromLocation(location: Location): String? {
            return "[" + Location.convert(location.latitude, Location.FORMAT_DEGREES) +
                    ", " +
                    Location.convert(location.longitude, Location.FORMAT_DEGREES) + "]"
        }

    }
}