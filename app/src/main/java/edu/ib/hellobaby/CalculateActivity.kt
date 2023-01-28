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
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calculate_dates.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

class CalculateActivity : AppCompatActivity() {
    @SuppressLint("CommitPrefEdits")

    lateinit var radioButton: RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_dates)

        val sharedPrefFile = "kotlinsharedpreference"
        val sharedPreference =  getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        val editShared: SharedPreferences.Editor =  sharedPreference.edit()
        editShared.clear()
        editShared.commit()

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
            val dateMillis: Long = getDate.date
            val date = Date(dateMillis)
            Log.d("data", "$date")

            val sdf = SimpleDateFormat("yyyy-MM-dd")

            val selectedDay = DateFormat.format("dd", date).toString().toInt()
            val selectedMonthNumber = DateFormat.format("MM", date).toString().toInt()
            val selectedYear = DateFormat.format("yyyy", date).toString().toInt()
            var localDate = LocalDate.of(selectedYear, selectedMonthNumber, selectedDay)

            val pregnacy = Period.of(0, 0, 270)
            val genderLength = Period.of(0,0,140)
            val showerLength = Period.of(0,0,210)
            val showerLengthMax = Period.of(0, 0, 224)

            val intSelectButton: Int = radio_group!!.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)
            val radioText = radioButton.text
            if (radioText == "Poród") {
                val pregnacyStart = localDate.minus(pregnacy)
                localDate = pregnacyStart
            }
            Toast.makeText(baseContext, "Obliczono datę", Toast.LENGTH_SHORT).show()

            val birthDate = localDate.plus(pregnacy)
            val birthDateMillis: Date = sdf.parse(birthDate.toString())
            val millis: Long = birthDateMillis.time

            //gender reveal po USG  ok 20 tyg
            val genderReveal = localDate.plus(genderLength)
            Log.d("gender", "$genderReveal")

            // baby shower
            val babyShower = localDate.plus(showerLength)
            val babyShowerMax = localDate.plus(showerLengthMax)
            Log.d("baby", "$babyShower")

            // Save dates
            val editor:SharedPreferences.Editor =  sharedPreference.edit()
            editor.putLong("periodDate", dateMillis)
            editor.putString("birthDate", birthDate.toString())
            editor.putString("genderRevealDate", genderReveal.toString())
            editor.putString("babyShowerDate", babyShower.toString())
            editor.putString("babyShowerMax", babyShowerMax.toString())
            editor.putLong("birthMillis", millis)
            editor.putString("sexDate", localDate.toString())
            editor.apply()
            editor.commit()

            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}