package com.example.alcoholcounter.ui.events;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alcoholcounter.Helpers
import com.example.alcoholcounter.MainApp
import com.example.alcoholcounter.R
import kotlinx.android.synthetic.main.fragment_event.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


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
        private var price : TextView = itemView.findViewById(R.id.eventPrize)
        private var timeText : TextView = itemView.findViewById(R.id.eventTime)
        private var locationText : TextView = itemView.findViewById(R.id.eventLocation)

        fun bind(event: Event, clickListener: OnClickListener) {
            val locale = Locale.getDefault()
            val currency = Currency.getInstance(locale)
            val dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale)

            var time = ""
            if(event.timeFrom != null){
                time += dateFormat.format(event.timeFrom!!)
            }
            if(event.timeFrom != null && event.timeTo != null){
                time += " - "
            }
            if(event.timeTo != null){
                time += dateFormat.format(event.timeTo!!)
            }

            title.text = event.title
            timeText.text = time
            price.text = String.format("%s %s", event.totalPrice.toString(), currency.symbol)
            if(event.location != null){
                locationText.text = Helpers.stringFromLocation(event.location!!)
            }
            itemView.setOnClickListener{
                clickListener.onItemClicked(event)
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(event: Event)
    }
}
