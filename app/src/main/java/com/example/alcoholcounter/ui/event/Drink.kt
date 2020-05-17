package com.example.alcoholcounter.ui.event

import java.math.BigDecimal
import java.sql.Time


class Drink(var name: String) {
    var type : String = ""
    var amount : Int = 0
    var lastAdded: ArrayList<Time> = ArrayList()
    var price : BigDecimal = BigDecimal.ZERO

    init {
    }
}