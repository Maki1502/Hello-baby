package edu.ib.hellobaby

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.icu.text.MessageFormat.format
import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.DateFormat.format
import android.util.Log
import android.widget.Adapter
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_calculate_dates.*
import java.lang.String.format

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*


class CalculateDates : AppCompatActivity() {
    @SuppressLint("CommitPrefEdits") // to sie samo pojawilo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_dates)

        val sharedPrefFile = "kotlinsharedpreference"
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)

        // Function to get actual date from system calendar
        getDate.setOnDateChangeListener { calView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->
            // Create calender object with which will have system date time.
            val calender: Calendar = Calendar.getInstance()
            // Set attributes in calender object as per selected date.
            calender.set(year, month, dayOfMonth)
            // Now set calenderView with this calender object to highlight selected date on UI.
            calView.setDate(calender.timeInMillis, true, true)
            Log.d("data systemowa", "$calender")
        }

        // Function to get date from calendar
        calcDatesButton.setOnClickListener {
            // Fetch long milliseconds from calenderView.
            val dateMillis: Long = getDate.date
            // Create Date object from milliseconds.
            val date = Date(dateMillis)
            Log.d("data", "$date")

            var selectedDay = DateFormat.format("dd", date).toString().toInt()
            var selectedMonthNumber = DateFormat.format("MM", date).toString().toInt()
            val selectedYear = DateFormat.format("yyyy", date).toString().toInt()
            var localDate = LocalDate.of(selectedYear, selectedMonthNumber, selectedDay)

            // Save period date
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putLong("periodDate", dateMillis)

            var pregnacy = Period.of(0, 0, 270)
            var birthDate = localDate.plus(pregnacy)
            Log.d("mod date", "$birthDate")

            //gender reveal po USG  ok 20 tyg
            val genderLength = Period.of(0,0,140)
            val genderReveal = localDate.plus(genderLength)
            Log.d("gender", "$genderReveal")

            // baby shower
            val showerLength = Period.of(0,0,210)
            val babyShower = localDate.plus(showerLength)
            Log.d("baby", "$babyShower")
        }

    }

}