package com.example.alcoholcounter.ui.events

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alcoholcounter.Helpers
import com.example.alcoholcounter.MainActivity
import com.example.alcoholcounter.MainApp
import com.example.alcoholcounter.R
import com.example.alcoholcounter.ui.drinks.DrinkDB
import com.example.alcoholcounter.ui.drinks.DrinkListAdapter
import kotlinx.android.synthetic.main.fragment_event.*
import java.text.DateFormat
import java.util.*


class EventFragment(val event : Event) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.event_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                val frag =
                    EventEditFragment(event)
                (activity as MainActivity).replaceFragment(frag)
                return true
            }
            R.id.delete -> {
                AlertDialog.Builder(context)
                    .setTitle("Delete?")
                    .setMessage("Do you really want to delete this event?")
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .setPositiveButton(
                        android.R.string.yes
                    ) { dialog, whichButton ->
                        MainApp.dataHandler.events.remove(event)
                        val fragmentManager = requireActivity().supportFragmentManager
                        fragmentManager.popBackStackImmediate()
                    }
                    .setNegativeButton(android.R.string.no, null).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter =
            DrinkListAdapter(event.drinks)
        drinkListRecycler.adapter = adapter

        fab.setOnClickListener {
            event.drinks.addAll(DrinkDB())
            adapter.notifyDataSetChanged();
        }

        eventTitle.text = event.title
        val locale = Locale.getDefault()
        val currency = Currency.getInstance(locale)
        val dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);

        if(event.timeFrom != null){
            eventTime.text = String.format("%s - %s", dateFormat.format(event.timeFrom!!), dateFormat.format(event.timeTo!!))
        }

        eventPrice.text = String.format("%s %s", event.totalPrice.toString(), currency.symbol)
        showLocationButton.visibility = View.GONE
        if(event.location != null){
            eventLocation.text = Helpers.stringFromLocation(event.location!!)
            showLocationButton.visibility = View.VISIBLE
            showLocationButton.setOnClickListener {
                // TODO
            }
        }
        drinkListRecycler.layoutManager = LinearLayoutManager(context)
    }
}