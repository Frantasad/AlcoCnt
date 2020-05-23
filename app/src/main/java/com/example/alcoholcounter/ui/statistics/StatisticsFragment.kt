package com.example.alcoholcounter.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.alcoholcounter.MainApp
import com.example.alcoholcounter.R
import cz.pv239.seminar2.EventDB
import kotlinx.android.synthetic.main.fragment_statistics.*

class StatisticsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = view.findViewById(R.id.text_statistics)
        textView.text = "Stistics"
        button.setOnClickListener{
            val data = MainApp.dataHandler!!
            data.events.clear()
            data.events.addAll(EventDB())
            data.saveEvents()
        }
    }
}
