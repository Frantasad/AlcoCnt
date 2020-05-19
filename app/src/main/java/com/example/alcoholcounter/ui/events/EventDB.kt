package cz.pv239.seminar2

import com.example.alcoholcounter.ui.event.Drink
import com.example.alcoholcounter.ui.events.Event
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class EventDB : ArrayList<Event>() {
    init {
        add(Event("Kalba", Calendar.getInstance().time, Calendar.getInstance().time, arrayListOf(Drink("Baran 12°"))))
        add(Event("Parba"))
        add(Event("Mejdlo"))
        add(Event("Chlastacka"))
        add(Event("Voziracka"))
    }
}
