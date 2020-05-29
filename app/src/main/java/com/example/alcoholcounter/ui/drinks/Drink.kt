package com.example.alcoholcounter.ui.drinks

import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList


class Drink(var name: String) {
    var type : String = ""
    var amount : Int = 0
    var lastAdded: ArrayList<Date> = ArrayList()
    var price : BigDecimal = BigDecimal.ZERO

    init {
    }
}