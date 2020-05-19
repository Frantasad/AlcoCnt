package com.example.alcoholcounter.ui.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alcoholcounter.R
import com.example.alcoholcounter.ui.events.Event
import kotlinx.android.synthetic.main.fragment_event.*
import java.text.DateFormat
import java.util.*

class EventFragment(val event : Event) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drinkListRecycler.adapter = DrinkListAdapter(event.drinks)
        eventTitle.text = event.title

        val locale = Locale.getDefault()
        val currency = Currency.getInstance(locale)

        val dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);

        if(event.timeFrom != null){
            eventTime.text = String.format("%s - %s", dateFormat.format(event.timeFrom!!), dateFormat.format(event.timeTo!!))
        }

        eventPrice.text = String.format("%s %s", event.totalPrice.toString(), currency.symbol) //event.totalPrice.toString()

        drinkListRecycler.layoutManager = LinearLayoutManager(context)
    }
}