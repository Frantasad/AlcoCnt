package com.example.alcoholcounter.ui.events

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alcoholcounter.Helpers
import com.example.alcoholcounter.MainActivity
import com.example.alcoholcounter.MainApp
import com.example.alcoholcounter.R
import com.example.alcoholcounter.ui.drinks.Drink
import com.example.alcoholcounter.ui.drinks.DrinkEditFragment
import com.example.alcoholcounter.ui.drinks.DrinkListAdapter
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_event.fab
import java.text.DateFormat
import java.util.*


class EventFragment(val event : Event) : Fragment(),
    EventEditFragment.OnEditedListener,
    DrinkListAdapter.OnMenuClickListener,
    DrinkEditFragment.OnItemChangedListener
{
    private lateinit var drinkListAdapter: DrinkListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                val frag = EventEditFragment(event)
                (activity as MainActivity).replaceFragment(frag)
                return true
            }
            R.id.delete -> {
                AlertDialog.Builder(context)
                    .setTitle(getString(R.string.delete))
                    .setMessage(getString(R.string.deleteEventMessage))
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
        drinkListAdapter = DrinkListAdapter(event.drinks, this, this)
        drinkListRecycler.adapter = drinkListAdapter

        fab.setOnClickListener {
            val frag = DrinkEditFragment(null, event)
            frag.onEditedListener = this
            (activity as MainActivity).replaceFragment(frag)
        }

        eventTitle.text = event.title
        val locale = Locale.getDefault()
        val currency = Currency.getInstance(locale)
        val dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);

        val from = event.timeFrom
        val to = event.timeTo

        var time = ""
        val timeFrom = event.timeFrom
        val timeTo = event.timeTo
        if(timeFrom != null){
            time += dateFormat.format(timeFrom)
        }
        if(timeFrom != null && timeTo != null){
            time += " - "
        }
        if(timeTo != null){
            time += dateFormat.format(timeTo)
        }
        eventTime.text = time

        eventPrice.text = String.format("%s %s", event.totalPrice.toString(), currency.symbol)

        val loc = event.location
        if(loc != null){
            eventLocation.text = Helpers.stringFromLocation(loc)
        } else {
            eventLocation.text = getString(R.string.unknown_location)
        }
        drinkListRecycler.layoutManager = LinearLayoutManager(context)
    }

    override fun onEditConfirmClicked() {
        drinkListAdapter.notifyDataSetChanged()
        drinkListRecycler.smoothScrollToPosition(event.drinks.size - 1)
    }

    override fun onMenuClicked(drink: Drink, button: View) {
        button.setOnClickListener {
            val popup = PopupMenu(context, button)
            popup.menuInflater.inflate(R.menu.edit_menu, popup.menu)
            popup.show()

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.edit -> {
                        val frag = DrinkEditFragment(drink)
                        (activity as MainActivity).replaceFragment(frag)
                    }
                    R.id.delete -> {
                        AlertDialog.Builder(context )
                            .setTitle(requireActivity().getString(R.string.delete))
                            .setMessage(requireActivity().getString(R.string.deleteDrinkMessage))
                            .setIcon(R.drawable.ic_warning_black_24dp)
                            .setPositiveButton(
                                android.R.string.yes
                            ) { dialog, whichButton ->
                                event.drinks.remove(drink)
                                drinkListAdapter.notifyDataSetChanged()
                                onItemChanged()
                            }
                            .setNegativeButton(android.R.string.no, null)
                            .show()
                    }
                }
                true
            }
        }
    }

    override fun onItemChanged() {
        val locale = Locale.getDefault()
        val currency = Currency.getInstance(locale)

        eventPrice.text = String.format("%s %s", event.totalPrice.toString(), currency.symbol)
    }
}