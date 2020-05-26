package com.example.alcoholcounter.ui.events;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alcoholcounter.R


class EventListAdapter(
    private val events: ArrayList<Event>,
    private val clickListener : OnClickListener
) : RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.events_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position], clickListener)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var title : TextView = itemView.findViewById(R.id.eventTitle)

        fun bind(event: Event, clickListener: OnClickListener) {
            title.text = event.title
            itemView.setOnClickListener{
                clickListener.onItemClicked(event)
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(event: Event)
    }
}
