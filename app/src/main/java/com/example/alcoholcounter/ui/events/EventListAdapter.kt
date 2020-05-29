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
    private val clickListener : OnClickListener
) : RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.events_list_item, parent, false), this)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position], clickListener)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    class EventViewHolder(itemView: View, val ownerAdapter : EventListAdapter) : RecyclerView.ViewHolder(itemView){
        private var title : TextView = itemView.findViewById(R.id.eventTitle)
        private var price : TextView = itemView.findViewById(R.id.eventPrize)
        private var timeText : TextView = itemView.findViewById(R.id.eventTime)
        private var locationText : TextView = itemView.findViewById(R.id.eventLocation)

        var menuButton: ImageButton = itemView.findViewById(R.id.menuButton)

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
            } else {
                locationText.text = itemView.context.getString(R.string.unknown_location)
            }

            itemView.setOnClickListener{
                clickListener.onItemClicked(event)
            }

            menuButton.setOnClickListener {
                val popup = PopupMenu(itemView.context, menuButton)
                popup.menuInflater.inflate(R.menu.event_menu, popup.menu)
                popup.show()

                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.edit -> {
                            val frag = EventEditFragment(event)
                            (itemView.context as MainActivity).replaceFragment(frag)
                        }
                        R.id.delete -> {
                            AlertDialog.Builder(itemView.context )
                                .setTitle(itemView.context.getString(R.string.delete))
                                .setMessage(itemView.context.getString(R.string.deleteEventMessage))
                                .setIcon(R.drawable.ic_warning_black_24dp)
                                .setPositiveButton(
                                    android.R.string.yes
                                ) { dialog, whichButton ->
                                    MainApp.dataHandler.events.remove(event)
                                    val fragmentManager = (itemView.context as MainActivity).supportFragmentManager
                                    fragmentManager.popBackStackImmediate()
                                    ownerAdapter.notifyDataSetChanged()
                                }
                                .setNegativeButton(android.R.string.no, null)
                                .show()
                        }
                    }
                    true
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(event: Event)
    }

    interface OnMenuClickListener {
        fun onItemClicked(event: Event)
    }
}
