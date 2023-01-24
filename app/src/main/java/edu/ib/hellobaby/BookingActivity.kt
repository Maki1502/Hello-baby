package edu.ib.hellobaby

import android.app.ActionBar
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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

        setupSpinner()

        bookButton.setOnClickListener {
            var startHour = spinnerHours.selectedItem.toString().toInt()
            startHour -= 1
            val startMin = spinnerMin.selectedItem.toString().toInt()
            val durationString = spinner_durat.selectedItem.toString()

            val dateMillis: Long = bookingCalendar.date
            val date = Date(dateMillis)
            val selectedDay = DateFormat.format("dd", date).toString().toInt()
            var selectedMonthNumber = DateFormat.format("MM", date).toString().toInt()
            selectedMonthNumber -= 1
            val selectedYear = DateFormat.format("yyyy", date).toString().toInt()

            val calID: Long = 4

            var startMillis: Long
            var endMillis: Long

            if (durationString == "Cały dzień") {
                startMillis = Calendar.getInstance().run {
                    set(selectedYear, selectedMonthNumber, selectedDay, startHour, 0)
                    timeInMillis
                }
                endMillis = Calendar.getInstance().run {
                    set(selectedYear, selectedMonthNumber, selectedDay, 23, 59)
                    timeInMillis
                }
            }
            else {
                val duration = durationString.toInt()
                startMillis = Calendar.getInstance().run {
                    set(selectedYear, selectedMonthNumber, selectedDay, startHour, startMin)
                    timeInMillis
                }
                endMillis = Calendar.getInstance().run {
                    set(selectedYear, selectedMonthNumber, selectedDay, startHour, startMin+duration)
                    timeInMillis
                }
            }

            val title = spinnerTitle.selectedItem.toString()

            if (title == "Inne") {
                val description = et_description.text.toString()
                val values = ContentValues().apply {
                    put(CalendarContract.Events.DTSTART, startMillis)
                    put(CalendarContract.Events.DTEND, endMillis)
                    put(CalendarContract.Events.TITLE, title)
                    put(CalendarContract.Events.DESCRIPTION, description)
                    put(CalendarContract.Events.CALENDAR_ID, calID)
                    put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Warsaw")
                }
                val uri: Uri? = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
                Toast.makeText(this, "Dodano do kalendarza", Toast.LENGTH_SHORT).show()
            } else {

                val values = ContentValues().apply {
                    put(CalendarContract.Events.DTSTART, startMillis)
                    put(CalendarContract.Events.DTEND, endMillis)
                    put(CalendarContract.Events.TITLE, title)
                    put(CalendarContract.Events.CALENDAR_ID, calID)
                    put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Warsaw")
                }
                val uri: Uri? = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
                Toast.makeText(this, "Dodano do kalendarza", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSpinner(){
        val activitiesNames = arrayOf("Konsultacja położnicza", "USG", "USG 3D",
            "USG prenatalne I / II / III trymestru", "KTG", "Badania genetyczne", "Echo serca płodu",
            "Test zintegrowany I-go trymestru wg FMF", "Indeks oceny wydolności łożyska i preeklampsji sFlt-1/PIGF",
            "Szczepienie", "Szkoła rodzenia", "Konsultacja położnej", "Porada laktacyjna", "Badania laboraotryjne",
            "Test ryzyka porodu przedwczesnego", "Inne" )
        val spinner = spinnerTitle
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, activitiesNames)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = activitiesNames[position]
                if (selectedItem == "Inne") {
                    val layout = findViewById<LinearLayout>(R.id.et_layout)
                val params: LinearLayout.LayoutParams = layout.layoutParams as LinearLayout.LayoutParams
                params.height = 200
                params.width = 600
                layout.layoutParams = params
                } else {
                    val layout = findViewById<LinearLayout>(R.id.et_layout)
                val params: LinearLayout.LayoutParams = layout.layoutParams as LinearLayout.LayoutParams
                params.height = 0
                params.width = 0
                layout.layoutParams = params
                }
                Log.v("aktywność", "$selectedItem")
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
    }
}