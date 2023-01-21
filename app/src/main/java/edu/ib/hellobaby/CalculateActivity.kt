package edu.ib.hellobaby

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.CalendarView
import kotlinx.android.synthetic.main.activity_calculate_dates.*
import java.time.LocalDate
import java.time.Period
import java.util.*

class CalculateActivity : AppCompatActivity() {
    @SuppressLint("CommitPrefEdits") // to sie samo pojawilo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_dates)

        val sharedPrefFile = "kotlinsharedpreference"
        // val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
        //    Context.MODE_PRIVATE)
        val sharedPreference =  getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        // Function to get actual date from system calendar
        getDate.setOnDateChangeListener { calView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->
            // Create calender object with which will have system date time.
            val calender: Calendar = Calendar.getInstance()
            // Set attributes in calender object as per selected date.
            calender.set(year, month, dayOfMonth)
            // Now set calenderView with this calender object to highlight selected date on UI.
            calView.setDate(calender.timeInMillis, true, true)
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

            var pregnacy = Period.of(0, 0, 270)
            var birthDate = localDate.plus(pregnacy)
            Log.d("mod date", "$birthDate")

            //gender reveal po USG  ok 20 tyg
            val genderLength = Period.of(0,0,140)
            val genderReveal = localDate.plus(genderLength)
            Log.d("gender", "$genderReveal")

            // baby shower
            val showerLength = Period.of(0,0,210)
            val showerLengthMax = Period.of(0, 0, 224)
            val babyShower = localDate.plus(showerLength)
            val babyShowerMax = localDate.plus(showerLengthMax)
            Log.d("baby", "$babyShower")

            // Save dates
            val editor: SharedPreferences.Editor =  sharedPreference.edit()
            editor.putLong("periodDate", dateMillis)
            editor.putString("birthDate", birthDate.toString())
            editor.putString("genderRevealDate", genderReveal.toString())
            editor.putString("babyShowerDate", babyShower.toString())
            editor.apply()
            editor.commit()

            val list: MutableList<String> = ArrayList()
            list.add("Spodziewana data porodu to: $birthDate")
            list.add("Baby shower zorganizuj pomiędzy:   $babyShower   a   $babyShowerMax")
            list.add("Oświadczyny płciowe możesz zorganizować po: $genderReveal")
            // Set adapter to viewPager.
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}