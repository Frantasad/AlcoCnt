package com.example.alcoholcounter.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.alcoholcounter.MainActivity
import com.example.alcoholcounter.MainApp
import com.example.alcoholcounter.R
import kotlinx.android.synthetic.main.fragment_statistics.*
import java.math.BigDecimal
import java.util.*

class StatisticsFragment : Fragment() {
    private val numberOfEvents: Int
        get() { return MainApp.dataHandler.events.size }

    private val numberOfDrinks: Int get() {
        var drinkCount: Int = 0
        for (event in MainApp.dataHandler.events) {
            for (drink in event.drinks) {
                drinkCount += drink.amount
            }
        }
        return drinkCount
    }

    private val totalCost: BigDecimal
        get(){
            var total = BigDecimal.ZERO
            for (event in MainApp.dataHandler.events) {
                total += event.totalPrice
            }
            return total
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setActionBarTitle(getString(R.string.title_statistics))
        val locale = Locale.getDefault()
        val currency = Currency.getInstance(locale)

        number_of_events.text = numberOfEvents.toString()
        number_of_drinks.text = numberOfDrinks.toString()
        total_price.text = String.format("%s %s", totalCost.toString(), currency.symbol)
    }
}
