package com.example.alcoholcounter.ui.events

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alcoholcounter.DataHandler
import com.example.alcoholcounter.MainActivity
import com.example.alcoholcounter.R
import com.example.alcoholcounter.ui.event.EventFragment
import kotlinx.android.synthetic.main.fragment_eventlist.*
import com.example.alcoholcounter.MainApp
import com.example.alcoholcounter.ui.event.Drink
import java.util.*

class EventListFragment : Fragment(), EventListAdapter.OnEventClickListener {

    private lateinit var events : ArrayList<Event>
    private lateinit var eventListAdapter : EventListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_eventlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        events = MainApp.dataHandler.events
        eventListAdapter = EventListAdapter(events, this)
        eventsListRecycler.adapter = eventListAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        eventsListRecycler.layoutManager = linearLayoutManager

        fab.setOnClickListener {
            events.add(Event("NOVE", Calendar.getInstance().time, Calendar.getInstance().time, arrayListOf(
                Drink("Test 12°")
            )))
            MainApp.dataHandler.events = events
            eventListAdapter.notifyDataSetChanged();
            eventsListRecycler.smoothScrollToPosition(events.size - 1);
        }
    }

    override fun onStart() {
        super.onStart()
        events = MainApp.dataHandler.events
        MainApp.dataHandler.loadEvents()
        eventListAdapter.notifyDataSetChanged();
    }

    override fun onStop() {
        super.onStop()
        MainApp.dataHandler.events = events
        MainApp.dataHandler.saveEvents()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.putInt(STATE_CLICKS, clicks)
    }

    override fun onItemClicked(event: Event) {
        (activity as MainActivity).replaceFragment(EventFragment(event))
    }

}
