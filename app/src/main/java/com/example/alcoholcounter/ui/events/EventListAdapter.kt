package com.example.alcoholcounter.ui.events;

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alcoholcounter.R


class EventListAdapter(private val events: List<Event>, private val onEventListener : OnEventListener) :
    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    private val _onEventListener : OnEventListener = onEventListener
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.events_list_item, parent, false), _onEventListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int {
        return events.size
    }

    class ViewHolder(itemView: View, onEventListener : OnEventListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        private val onEventListener : OnEventListener = onEventListener
        var title : TextView = itemView.findViewById(R.id.eventTitle)
        fun bind(event: Event) {
            title.text = event.title
        }

        override fun onClick(v: View?) {
            Log.d(ContentValues.TAG, "omfg")
            onEventListener.onEventClick(adapterPosition)
        }
    }

    interface OnEventListener {
        fun onEventClick(position: Int)
    }


}