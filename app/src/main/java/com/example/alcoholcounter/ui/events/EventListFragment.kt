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

class EventListFragment : Fragment(), EventListAdapter.OnEventClickListener {

    //private var adapter: EventListAdapter = EventListAdapter(EventDB(), this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_eventlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsListRecycler.adapter = EventListAdapter((requireActivity() as MainActivity).dataHandler.events, this)
        eventsListRecycler.layoutManager = LinearLayoutManager(context)
    }

    override fun onItemClicked(event: Event) {
        (activity as MainActivity).replaceFragment(EventFragment(event))
        Toast.makeText(context, event.title, Toast.LENGTH_LONG).show()
    }

}
