package com.example.alcoholcounter.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alcoholcounter.R
import kotlinx.android.synthetic.main.fragment_event.*


class EventFragment : Fragment() {

    private var adapter: DrinkListAdapter = DrinkListAdapter(DrinkDB())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drinkListRecycler.adapter = adapter
        drinkListRecycler.layoutManager = LinearLayoutManager(context)
    }
}