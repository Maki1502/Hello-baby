package edu.ib.hellobaby

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.CalendarView
import kotlinx.android.synthetic.main.activity_calculate_dates.*
import java.util.*

class CalculateDates : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_dates)

        getDate.setOnDateChangeListener { calView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->
            // Create calender object with which will have system date time.
            val calender: Calendar = Calendar.getInstance()
            // Set attributes in calender object as per selected date.
            calender.set(year, month, dayOfMonth)
            // Now set calenderView with this calender object to highlight selected date on UI.
            calView.setDate(calender.timeInMillis, true, true)
        }

        calcDatesButton.setOnClickListener {

            // Fetch long milliseconds from calenderView.
            val dateMillis: Long = getDate.date

            // Create Date object from milliseconds.
            val date = Date(dateMillis)

            val selectedDay = DateFormat.format("dd", date).toString().toInt()
            var selectedMonthNumber = DateFormat.format("MM", date).toString().toInt()
            selectedMonthNumber -= 1
            val selectedYear = DateFormat.format("yyyy", date).toString().toInt()
            Log.d("kurwamac", "$date")
        }

    }
}