package edu.ib.hellobaby

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_delete_events.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DeleteEvents : AppCompatActivity() {
    var checkBoxes = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_events)

        val sharedPrefFile = "kotlinsharedpreference"
        val sharedPreference =  getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val date1 = sharedPreference.getLong("periodDate", 0)
        val date2 = sharedPreference.getLong("birthMillis", 0)
        Log.d("DATY", "$date1   $date2")

        val utility = Utility.readCalendarEvent(this, date1, date2)
        val events = utility.first
        val dates = utility.second
        val ids = utility.third
        Log.d("Text", "$events")

        for (i in 0..events.size-1) {
            addCheckbox(events[i], dates[i], ids[i])
        }

        del_btn.setOnClickListener {
            Log.d("checkBoxes", "$checkBoxes")
            val eventID = checkBoxes.distinct()
            Log.d("NOWA LISTA", "$eventID")
            for (items in eventID) {
                deleteEvents(items)
            }
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    object Utility {
        private var nameOfEvent = ArrayList<String>()
        private var startDates = ArrayList<String>()
        private var idOfEvents = ArrayList<String>()

        @SuppressLint("Recycle")
        fun readCalendarEvent(context: Context, startTime: Long?, endTime: Long?): Triple<ArrayList<String>, ArrayList<String>, ArrayList<String>> {
            var selection =
                "(( " + CalendarContract.Events.DTSTART + " >= " + startTime + " ) AND " +
                        "( " + CalendarContract.Events.DTSTART + " <= " + endTime + " ) AND ( deleted != 1 ))"

            val cursor: Cursor? = context.contentResolver
                .query(
                    Uri.parse("content://com.android.calendar/events"), arrayOf(
                        "calendar_id", "title", "description",
                        "dtstart", "dtend", "eventLocation", "_id"
                    ), null,
                    null, null
                )
            cursor?.moveToFirst()
            // fetching calendars name
            val cNames = arrayOfNulls<String>(cursor?.count!!)

            // fetching calendars id
            nameOfEvent.clear()
            startDates.clear()
            idOfEvents.clear()
            for (i in cNames.indices) {
                nameOfEvent.add(cursor.getString(1))
                startDates.add(getDate(cursor.getString(3).toLong()))
                idOfEvents.add(cursor.getString(6))
                cNames[i] = cursor.getString(1)
                cursor.moveToNext()
            }

            Log.d("NAME OF EVENTS", "$nameOfEvent")

            val temporaryListEvents = ArrayList<String>()
            val temporaryListDates = ArrayList<String>()
            val temporaryListId = ArrayList<String>()
            var i:Int = 0

            for (item in nameOfEvent) {
                if ((item == "USG") || (item == "KTG")
                    || (item == "Konsultacja położnicza")
                    || item == "USG 3D // 4D"
                    || (item == "USG prenatalne I / II / III trymestru")
                    || (item == "Badania genetyczne")
                    || (item == "Echo serca płodu")
                    || (item == "Test zintegrowany I-go trymestru wg FMF")
                    || (item == "Indeks oceny wydolności łożyska i preeklampsji sFlt-1/PIGF")
                    || (item == "Szczepienie")
                    || (item == "Szkoła rodzenia")
                    || (item == "Konsultacja położnej")
                    || (item == "Porada laktacyjna")
                    || (item == "Badania laboraotryjne")
                    || (item == "Test ryzyka porodu przedwczesnego")
                    || (item == "Inne")
                ) {
                    temporaryListEvents.add(item)
                    temporaryListDates.add(startDates[i])
                    temporaryListId.add(idOfEvents[i])
                }
                i ++
            }
            val triple = Triple(temporaryListEvents, temporaryListDates, temporaryListId)
            return triple
        }

        @SuppressLint("SimpleDateFormat")
        private fun getDate(milliSeconds: Long): String {
            val formatter = SimpleDateFormat(
                "dd/MM/yyyy HH:mm"
            )
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = milliSeconds
            return formatter.format(calendar.time)
        }
    }

    private fun addCheckbox(hint:String, date: String, id: String) {
        // Create CheckBox
        val checkBox = CheckBox(this)
        checkBox.id = id.toInt()
        checkBox.hint = hint
        checkBox.text = "$hint   $date"
        checkBox.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        checkBox.setPadding(20, 20, 20, 20)
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) checkBoxes.add(checkBox.id.toString())
            val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Checkbox."
            Toast.makeText(this@DeleteEvents, msg, Toast.LENGTH_SHORT).show()
        }
        // Add CheckBox to LinearLayout
        checkBoxContainer.addView(checkBox)
    }

    fun deleteEvents(evID: String) {
        val eventID:Long = evID.toLong()
        Log.d("EVID", "$eventID")
        val deleteUri: Uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID)
        val rows: Int = contentResolver.delete(deleteUri, null, null)
    }
}