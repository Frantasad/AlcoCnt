package com.example.alcoholcounter.ui.events;
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alcoholcounter.Helpers
import com.example.alcoholcounter.MainActivity
import com.example.alcoholcounter.MainApp
import com.example.alcoholcounter.R
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_eventlist.view.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


class EventListAdapter(
    private val events: ArrayList<Event>,
    private val clickListener : OnClickListener,
    private val menuClickListener : OnMenuClickListener
) : RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.events_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position], clickListener, menuClickListener)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var title : TextView = itemView.findViewById(R.id.eventTitle)
        private var price : TextView = itemView.findViewById(R.id.eventPrize)
        private var timeText : TextView = itemView.findViewById(R.id.eventTime)
        private var locationText : TextView = itemView.findViewById(R.id.eventLocation)

        var menuButton: ImageButton = itemView.findViewById(R.id.menuButton)

        fun bind(event: Event, clickListener: OnClickListener, menuClickListener: OnMenuClickListener) {
            val locale = Locale.getDefault()
            val currency = Currency.getInstance(locale)
            val dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale)

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

            title.text = event.title
            timeText.text = time
            price.text = String.format("%s %s", event.totalPrice.toString(), currency.symbol)
            val loc = event.location
            if(loc != null){
                locationText.text = Helpers.stringFromLocation(loc)
            } else {
                locationText.text = itemView.context.getString(R.string.unknown_location)
            }

            itemView.setOnClickListener{
                clickListener.onItemClicked(event)
            }

            menuButton.setOnClickListener{
                menuClickListener.onItemClicked(event, menuButton)
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(event: Event)
    }

    interface OnMenuClickListener {
        fun onItemClicked(event: Event, button: View )
    }
}
