package com.example.alcoholcounter.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.alcoholcounter.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.pv239.seminar2.EventDb
import kotlinx.android.synthetic.main.fragment_events.*

class EventsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = EventListAdapter(EventDb())
        eventsListRecycler.adapter = adapter
        eventsListRecycler.layoutManager = LinearLayoutManager(view?.context)
    }
}
