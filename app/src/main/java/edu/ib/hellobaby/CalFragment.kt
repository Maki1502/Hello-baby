package edu.ib.hellobaby

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import kotlinx.android.synthetic.main.fragment_cal.*
import kotlinx.android.synthetic.main.fragment_cal.view.*
import java.util.*


class CalFragment : Fragment(R.layout.fragment_cal) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_cal, container, false)

        getDate.setOnDateChangeListener { calView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->

            // Create calender object with which will have system date time.
            val calender: Calendar = Calendar.getInstance()

            // Set attributes in calender object as per selected date.
            calender.set(year, month, dayOfMonth)

            // Now set calenderView with this calender object to highlight selected date on UI.
            calView.setDate(calender.timeInMillis, true, true)
            // Log.d("SelectedDate", "$dayOfMonth/${month + 1}/$year")

        }

        view.calcDatesButton.setOnClickListener { view ->

            val example = "xdddd"

            // Fetch long milliseconds from calenderView.
            val dateMillis: Long = getDate.date

            // Create Date object from milliseconds.
            val date = Date(dateMillis)

            val selectedDay = DateFormat.format("dd", date).toString().toInt()
            var selectedMonthNumber = DateFormat.format("MM", date).toString().toInt()
            selectedMonthNumber -= 1
            val selectedYear = DateFormat.format("yyyy", date).toString().toInt()

            // Log.d("SelectedDate", "$selectedDay oraz ${selectedMonthNumber + 1} oraz $selectedYear")
            print(example)

            testText.text = "nothing"

        }
        return view
    }
}