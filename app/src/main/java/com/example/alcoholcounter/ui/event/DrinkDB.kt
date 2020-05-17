package com.example.alcoholcounter.ui.event

import com.example.alcoholcounter.ui.event.Drink
import java.util.*

class DrinkDB : ArrayList<Drink>() {

    val names: List<String>
        get() = map { it.name }.toList()

    init {
        add(Drink("Baran 12°"))
        add(Drink("Poutník 12°"))
        add(Drink("Chotěboř 12°"))
        add(Drink("Beskydské Hořké 15°"))
        add(Drink("Kout 10°"))
    }
}