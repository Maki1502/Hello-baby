package edu.ib.hellobaby

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_zaimki.*

class ZaimkiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zaimki)

        val zaimki = resources.getStringArray(R.array.zaimki)
        val wiek = resources.getStringArray(R.array.wiek)
        val plec = resources.getStringArray(R.array.plec)

        if (zaimki_spin != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, zaimki)
            zaimki_spin.adapter = adapter

            zaimki_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    //val do wrzucenia wyniku
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
        }

        if (wiek_spin != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wiek)
            wiek_spin.adapter = adapter

            wiek_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    //val do wrzucenia wyniku
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
        }

        if (plec_spin != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, plec)
            plec_spin.adapter = adapter

            plec_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    //val do wrzucenia wyniku
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
        }

        save1_btn.setOnClickListener {
            //zapis wyników do shared
            startActivity(Intent(this, CalculateActivity::class.java))
        }
    }
}