package com.example.alcoholcounter.ui.events;

import com.example.alcoholcounter.ui.drinks.Drink
import java.math.BigDecimal
import java.util.*

data class Event(var title: String) {
    var ended: Boolean = false
    var timeFrom: Date? = null
    var timeTo: Date? = null
    var drinks: ArrayList<Drink> = ArrayList()
    var location: Pair<Double, Double>? = null // first = latitude, second = longitude
    val totalPrice: BigDecimal
        get(){
            var total = BigDecimal.ZERO
            for (drink in drinks) {
                total += drink.price * BigDecimal(drink.amount)
            }
            return total
        }

    constructor(title: String, timeFrom: Date, timeTo: Date, drinks: ArrayList<Drink>) : this(title) {
        this.timeFrom = timeFrom
        this.timeTo = timeTo
        this.drinks = drinks
    }

}


