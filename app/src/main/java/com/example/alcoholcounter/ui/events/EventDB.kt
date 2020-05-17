package cz.pv239.seminar2

import com.example.alcoholcounter.ui.events.Event
import java.util.*

class EventDB : ArrayList<Event>() {

    val names: List<String>
        get() = map { it.title }.toList()

    init {
        add(Event("Kalba"))
        add(Event("Parba"))
        add(Event("Mejdlo"))
        add(Event("Chlastacka"))
        add(Event("Voziracka"))
    }
}
