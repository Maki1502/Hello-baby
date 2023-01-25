package edu.ib.hellobaby

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_zaimki.*

class ZaimkiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zaimki)

        val sharedPreference =  getSharedPreferences("shareddane", Context.MODE_PRIVATE)
        val editShared: SharedPreferences.Editor =  sharedPreference.edit()
        editShared.clear()
        editShared.commit()

        val sharedPrefFile = "kotlinsharedpreference"
        val sharedPreference2 =  getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editShared2: SharedPreferences.Editor =  sharedPreference2.edit()
        editShared2.clear()
        editShared2.commit()

        save1_btn.setOnClickListener {

            val zaimki = zaimki_spin.selectedItem.toString()
            val zaimkiPos = zaimki_spin.selectedItemPosition
            val wiek = wiek_spin.selectedItem.toString()
            val wiekPos = wiek_spin.selectedItemPosition
            val plec = plec_spin.selectedItem.toString()
            val plecPos = plec_spin.selectedItemPosition
            val imiePos = bb_spin.selectedItemPosition
            val imie = bb_name.text.toString()
            val plecDziecka = bb_spin.selectedItem.toString()

            val editor: SharedPreferences.Editor =  sharedPreference.edit()
            editor.putString("zaimki",zaimki)
            editor.putInt("zaimkiSel",zaimkiPos)
            editor.putString("wiek",wiek)
            editor.putInt("wiekSel",wiekPos)
            editor.putString("plec",plec)
            editor.putInt("plecSel",plecPos)
            editor.putInt("imieSel",imiePos)
            editor.putString("nazwa",imie)
            editor.putString("plecDziecka", plecDziecka)
            editor.apply()
            editor.commit()

            startActivity(Intent(this, CalculateActivity::class.java))
        }
    }
}