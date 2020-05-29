package com.example.alcoholcounter.ui.events

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.alcoholcounter.Helpers
import com.example.alcoholcounter.MainApp
import com.example.alcoholcounter.R
import kotlinx.android.synthetic.main.fragment_event_edit.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


class EventEditFragment(val event : Event?) : Fragment(){

    private lateinit var startCalendar : Calendar
    private lateinit var endCalendar : Calendar

    private var location : Pair<Double, Double>? = null

    private val locationPicked : Boolean = false

    var onEditedListener : OnEditedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCalendar = Calendar.getInstance()
        endCalendar = Calendar.getInstance()

        if(event != null){
            nameEditText.setText(event.title)

            if(event.timeFrom != null){
                startCalendar.time = event.timeFrom!!
            }
            if(event.timeTo != null){
                endCalendar.time = event.timeTo!!
            }

            if(event.location != null){
                location = event.location!!
                pickLocationButton.text = Helpers.stringFromLocation(location!!)
            }
        }

        val locale = Locale.getDefault()
        val dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale)

        startTimePickButton.text = dateFormat.format(startCalendar.time)
        endTimePickButton.text = dateFormat.format(endCalendar.time)

        confirm_button.setOnClickListener{
            if(nameEditText.text.isNotEmpty() && pickLocationProgress.visibility == View.GONE){
                if(event == null){
                    createEvent()
                }else{
                    editEvent()
                }
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStackImmediate()
                onEditedListener?.onEditConfirmClicked()
            } else {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Invalid data!")
                    .setCancelable(false)
                    .setPositiveButton("OK"
                    ) { dialog, id -> }
                val alert: AlertDialog = builder.create()
                alert.show()
            }
        }

        startTimeNowButton.setOnClickListener {
            startCalendar = Calendar.getInstance()
            startTimePickButton.text = dateFormat.format(startCalendar.time)
        }

        endTimeNowButton.setOnClickListener {
            endCalendar = Calendar.getInstance()
            endTimePickButton.text = dateFormat.format(endCalendar.time)
        }

        setDateTimePicker(startTimePickButton, startCalendar)
        setDateTimePicker(endTimePickButton, endCalendar)
        pickLocationButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(MainApp.appContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 666)
                pickLocationButton.text = requireContext().resources.getString(R.string.unknown_location)
            }
            else {
                pickLocationProgress.visibility = View.VISIBLE
                pickLocationButton.isEnabled = false
                MainApp.getCurrentLocation { location ->
                    this.location = Pair(location.latitude, location.longitude)
                    pickLocationProgress.visibility = View.GONE
                    pickLocationButton.isEnabled = true
                    pickLocationButton.text = Helpers.stringFromLocation(this.location!!)
                }
            }


        }
    }

    private fun createEvent(){
        val newEvent = Event(
            nameEditText.text.toString(),
            startCalendar.time,
            endCalendar.time,
            ArrayList()
        )
        newEvent.location = location
        MainApp.dataHandler.events.add(newEvent)
    }

    private fun editEvent(){
        if(event != null){
            event.title = nameEditText.text.toString()
            event.timeFrom = startCalendar.time
            event.timeTo = endCalendar.time
            event.location = location
        }
    }

    private fun setDateTimePicker(button: Button, calendar: Calendar){
        val locale = Locale.getDefault()
        val dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale)

        button.setOnClickListener {
            val mYear= calendar.get(Calendar.YEAR)
            val mMonth = calendar.get(Calendar.MONTH)
            val mDay = calendar.get(Calendar.DAY_OF_MONTH)
            val mHour = calendar[Calendar.HOUR_OF_DAY]
            val mMinute = calendar[Calendar.MINUTE]

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    calendar.set(year, monthOfYear, dayOfMonth)
                    val timePickerDialog = TimePickerDialog(
                        requireContext(),
                        OnTimeSetListener { _, hourOfDay, minute ->
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            calendar.set(Calendar.MINUTE, minute)
                            button.text = dateFormat.format(calendar.time)
                        },
                        mHour,
                        mMinute,
                        true
                    )
                    calendar.set(Calendar.HOUR_OF_DAY, mHour)
                    calendar.set(Calendar.MINUTE, mMinute)
                    timePickerDialog.show()
                }, mYear, mMonth, mDay
            )
            calendar.set(mYear, mMonth, mDay)
            datePickerDialog.show()
        }
    }

    interface OnEditedListener {
        fun onEditConfirmClicked()
    }

}
