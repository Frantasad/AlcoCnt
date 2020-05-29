package com.example.alcoholcounter.ui.events

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alcoholcounter.MainActivity
import com.example.alcoholcounter.MainApp
import com.example.alcoholcounter.R
import kotlinx.android.synthetic.main.fragment_eventlist.*
import java.util.*


class EventListFragment : Fragment(),
    EventListAdapter.OnClickListener,
    EventEditFragment.OnEditedListener {

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
            val frag =
                EventEditFragment(null)
            frag.onEditedListener = this
            (activity as MainActivity).replaceFragment(frag)
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
        (activity as MainActivity).replaceFragment(
            EventFragment(
                event
            )
        )
    }

    override fun onEditConfirmClicked() {
        eventListAdapter.notifyDataSetChanged()
        eventsListRecycler.smoothScrollToPosition(events.size - 1)
    }

}
