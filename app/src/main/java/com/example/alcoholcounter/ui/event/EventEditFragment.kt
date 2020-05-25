package com.example.alcoholcounter.ui.event

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.alcoholcounter.R
import kotlinx.android.synthetic.main.fragment_event_edit.*
import java.text.DateFormat
import java.util.*


class EventEditFragment : Fragment() {

    private lateinit var startCalendar : Calendar
    private lateinit var endCalendar : Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCalendar = Calendar.getInstance()
        endCalendar = Calendar.getInstance()

        val locale = Locale.getDefault()
        val dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale);

        startTimePickButton.text = dateFormat.format(startCalendar.time)
        endTimePickButton.text = dateFormat.format(endCalendar.time)

        startTimePickButton.setOnClickListener {
            val mYear= startCalendar.get(Calendar.YEAR)
            val mMonth = startCalendar.get(Calendar.MONTH)
            val mDay = startCalendar.get(Calendar.DAY_OF_MONTH)
            val mHour = startCalendar[Calendar.HOUR_OF_DAY]
            val mMinute = startCalendar[Calendar.MINUTE]

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    startCalendar.set(year, monthOfYear, dayOfMonth)
                    val timePickerDialog = TimePickerDialog(
                        requireContext(),
                        OnTimeSetListener { _, hourOfDay, minute ->
                            startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            startCalendar.set(Calendar.MINUTE, minute)
                            startTimePickButton.text = dateFormat.format(startCalendar.time)
                        },
                        mHour,
                        mMinute,
                        true
                    )
                    startCalendar.set(Calendar.HOUR_OF_DAY, mHour)
                    startCalendar.set(Calendar.MINUTE, mMinute)
                    timePickerDialog.show()
                }, mYear, mMonth, mDay
            )
            startCalendar.set(mYear, mMonth, mDay)
            datePickerDialog.show()
        }

        endTimePickButton.setOnClickListener {
            val mYear= endCalendar.get(Calendar.YEAR)
            val mMonth = endCalendar.get(Calendar.MONTH)
            val mDay = endCalendar.get(Calendar.DAY_OF_MONTH)
            val mHour = endCalendar[Calendar.HOUR_OF_DAY]
            val mMinute = endCalendar[Calendar.MINUTE]

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    endCalendar.set(year, monthOfYear, dayOfMonth)
                    val timePickerDialog = TimePickerDialog(
                        requireContext(),
                        OnTimeSetListener { _, hourOfDay, minute ->
                            endCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            endCalendar.set(Calendar.MINUTE, minute)
                            endTimePickButton.text = dateFormat.format(endCalendar.time)
                        },
                        mHour,
                        mMinute,
                        true
                    )
                    endCalendar.set(Calendar.HOUR_OF_DAY, mHour)
                    endCalendar.set(Calendar.MINUTE, mMinute)
                    timePickerDialog.show()
                }, mYear, mMonth, mDay
            )
            endCalendar.set(mYear, mMonth, mDay)
            datePickerDialog.show()
        }
    }
}
