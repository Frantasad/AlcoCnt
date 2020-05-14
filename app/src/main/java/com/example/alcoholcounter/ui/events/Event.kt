package com.example.alcoholcounter.ui.events;

import android.R.string
import android.icu.util.Currency


data class Event(val title: String) {
    // val title: String? = null
    val ended: Boolean = false
    val dateFrom: String? = null
    val dateTo: string? = null
    val timeFrom: String? = null
    val timeTo: String? = null
    val currency: Currency? = null
    val price = 0.0
}


