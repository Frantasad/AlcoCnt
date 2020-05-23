package com.example.alcoholcounter.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alcoholcounter.DataHandler
import com.example.alcoholcounter.MainActivity
import com.example.alcoholcounter.R
import com.example.alcoholcounter.ui.event.EventFragment
import cz.pv239.seminar2.EventDB
import kotlinx.android.synthetic.main.fragment_eventlist.*
import android.util.Log
import com.google.android.material.snackbar.Snackbar

class EventListFragment : Fragment(), EventListAdapter.OnEventClickListener {

    //private var adapter: EventListAdapter = EventListAdapter(EventDB(), this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_eventlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventListAdapter = EventListAdapter((requireActivity() as MainActivity).dataHandler.events, this)
        eventsListRecycler.adapter = eventListAdapter
        eventsListRecycler.layoutManager = LinearLayoutManager(context)

        fab.setOnClickListener {
            val data = (requireActivity() as MainActivity).dataHandler
            data.events.addAll(EventDB())
            eventListAdapter.notifyDataSetChanged();
            data.saveEvents()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.putInt(STATE_CLICKS, clicks)
    }

    override fun onItemClicked(event: Event) {
        (activity as MainActivity).replaceFragment(EventFragment(event))
    }

}
