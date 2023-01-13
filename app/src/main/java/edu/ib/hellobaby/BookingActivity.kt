package edu.ib.hellobaby

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.CalendarView
import kotlinx.android.synthetic.main.activity_booking.*
import java.util.*

class BookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        bookingCalendar.setOnDateChangeListener { calView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->
            val calender: Calendar = Calendar.getInstance()
            calender.set(year, month, dayOfMonth)
            calView.setDate(calender.timeInMillis, true, true)
            Log.d("SelectedDate", "$dayOfMonth/${month + 1}/$year")
        }

        bookButton.setOnClickListener {


            var startHour = spinner1.selectedItem.toString().toInt()
            startHour -= 1
            val startMin = spinner2.selectedItem.toString().toInt()
            var durationString = spinner3.selectedItem.toString()
            if (durationString == "Cały dzień") {
                var duration = 720
                Log.d("duration", "$duration")
            }
            else {
                var duration = durationString.toInt()
                Log.d("duration", "$duration")
            }

            // Fetch long milliseconds from calenderView.
            val dateMillis: Long = bookingCalendar.date
            // Create Date object from milliseconds.
            val date = Date(dateMillis)
            // Split date
            val selectedDay = DateFormat.format("dd", date).toString().toInt()
            var selectedMonthNumber = DateFormat.format("MM", date).toString().toInt()
            selectedMonthNumber -= 1
            val selectedYear = DateFormat.format("yyyy", date).toString().toInt()
            Log.d("SelectedDate", "$selectedDay oraz ${selectedMonthNumber + 1} oraz $selectedYear")

            val calID: Long = 3


            // Create start and end date
            val startMillis: Long = Calendar.getInstance().run {
                set(selectedYear, selectedMonthNumber, selectedDay, startHour, startMin)
                timeInMillis
            }
            val endMillis: Long = Calendar.getInstance().run {
                set(selectedYear, selectedMonthNumber, selectedDay, startHour+1, startMin+20)
                timeInMillis
            }

            val title = spinner4.selectedItem.toString()

            val values = ContentValues().apply {
                put(CalendarContract.Events.DTSTART, startMillis)
                put(CalendarContract.Events.DTEND, endMillis)
                put(CalendarContract.Events.TITLE, title)
               // put(CalendarContract.Events.EVENT_LOCATION, location)
               // put(CalendarContract.Events.DESCRIPTION, other)
                put(CalendarContract.Events.CALENDAR_ID, calID)
                put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Warsaw")
            }
            val uri: Uri? = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
        }

    }

}