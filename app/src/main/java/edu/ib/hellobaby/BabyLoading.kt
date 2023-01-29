package edu.ib.hellobaby

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_baby_loading.*
import java.text.SimpleDateFormat
import java.time.LocalDate

class BabyLoading : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baby_loading)

        settings_btn.setOnClickListener {
            val intent = Intent(this,CalculateDates::class.java)
            startActivity(intent)
        }

        val sharedPrefFile = "kotlinsharedpreference"
        val sharedPreference =  getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val sexDate = sharedPreference.getString("sexDate", "null")

        val sharedSettings =  getSharedPreferences("shareddane", Context.MODE_PRIVATE)
        val babyName = sharedSettings.getString("nazwa", "null")
        val gender = sharedSettings.getString("plecDziecka", "null")

        if (sexDate == "null") {
            Toast.makeText(applicationContext, "Wprowadź datę miesiączki", Toast.LENGTH_LONG ).show()
            val intent = Intent(this,CalculateActivity::class.java)
            startActivity(intent)
        } else {
            val sdf = SimpleDateFormat("yyyy-MM-dd")

            val current = LocalDate.now().toString()

            val sexDate1 = sdf.parse(sexDate)
            val currentDate = sdf.parse(current)
            val mDifference = kotlin.math.abs(currentDate.time - sexDate1.time)
            val mDifferenceDates = mDifference / (24 * 60 * 60 * 1000)
            val mDayDifference = mDifferenceDates.toString()
            val babyLoading = 100 * mDayDifference.toInt() / 270

            var message:String

            if (gender == "Ten") {
                message = "Twój $babyName jest gotowy w $babyLoading%."
            } else if (gender == "Ta") {
                message = "Twoja $babyName jest gotowa w $babyLoading%."
            } else if (gender == "To") {
                message = "Twoje $babyName jest gotowe w $babyLoading%"
            } else message = "Twój płód jest gotowy w $babyLoading%"

            loadingText.text = message

            val imageLevel = 10000 * mDayDifference.toInt()/270

            val mImageDrawable = cryImage.drawable as ClipDrawable
            mImageDrawable.level = imageLevel
        }
    }
}