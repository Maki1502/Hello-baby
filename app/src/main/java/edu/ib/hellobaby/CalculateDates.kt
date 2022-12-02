package edu.ib.hellobaby

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_calculate_dates.*
import java.util.*


class CalculateDates : AppCompatActivity() {
    @SuppressLint("CommitPrefEdits") // to sie samo pojawilo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_dates)

        val sharedPrefFile = "kotlinsharedpreference"
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)

        setupViewPager2()

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

 /*           val selectedDay = DateFormat.format("dd", date).toString().toInt()
            var selectedMonthNumber = DateFormat.format("MM", date).toString().toInt()
            selectedMonthNumber -= 1
            val selectedYear = DateFormat.format("yyyy", date).toString().toInt()*/

            // Save period date
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putLong("periodDate", dateMillis)

        }

    }

    private fun setupViewPager2() {
        val list: MutableList<String> = ArrayList()
        list.add("This is your First Screen")
        list.add("This is your Second Screen")
        list.add("This is your Third Screen")
        list.add("This is your Fourth Screen")

        val colorList: MutableList<String> = ArrayList()
        colorList.add("#00ff00")
        colorList.add("#ff0000")
        colorList.add("#0000ff")
        colorList.add("#f0f0f0")

        // Set adapter to viewPager.
        datesPager.adapter = PagerAdapter(this, list, colorList)
    }

}