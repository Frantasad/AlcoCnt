package com.example.alcoholcounter.ui.event

import android.icu.util.Currency
import java.sql.Time

enum class DrinkType {
    Beer, Wine, Vodka, Rum, Whiskey, Brandy
}

data class Drink(val title: String) {
    //val title: String? = null
    val type : DrinkType = DrinkType.Beer
    val amount : Int = 0
    val lastAdded: ArrayList<Time>? = null
    val currency: Currency? = null
    val price = 0.0
}