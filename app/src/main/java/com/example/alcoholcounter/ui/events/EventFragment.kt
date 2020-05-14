package com.example.alcoholcounter.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alcoholcounter.R
import cz.pv239.seminar2.EventDb
import kotlinx.android.synthetic.main.fragment_events.*

class EventFragment : Fragment() {

    private var adapter: EventListAdapter = EventListAdapter(EventDb())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsListRecycler.adapter = adapter
        eventsListRecycler.layoutManager = LinearLayoutManager(context)
    }
}