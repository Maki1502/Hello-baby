package edu.ib.hellobaby

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_zaimki.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val sharedPreference =  getSharedPreferences("shareddane", Context.MODE_PRIVATE)
        zaimki_spin2.setSelection(sharedPreference.getInt("zaimkiSel",0))
        wiek_spin2.setSelection(sharedPreference.getInt("wiekSel",0))
        plec_spin2.setSelection(sharedPreference.getInt("plecSel",0))
        bb_spin2.setSelection(sharedPreference.getInt("imieSel",0))
        bb_name2.setText(sharedPreference.getString("nazwa","null"))

        save2_btn.setOnClickListener {

            val zaimki = zaimki_spin2.selectedItem.toString()
            val zaimkiPos = zaimki_spin2.selectedItemPosition
            val wiek = wiek_spin2.selectedItem.toString()
            val wiekPos = wiek_spin2.selectedItemPosition
            val plec = plec_spin2.selectedItem.toString()
            val plecPos = plec_spin2.selectedItemPosition
            val imiePos = bb_spin2.selectedItemPosition
            val plecDziecka = bb_spin2.selectedItem.toString()
            val imie = bb_name2.text.toString()

            Log.d("plec dziecka", "$plecDziecka")

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

            Toast.makeText(this, "Dane zostały zaktualizowane", Toast.LENGTH_LONG).show()
        }

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        logout_profile_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        calc2_btn.setOnClickListener {
            startActivity(Intent(this, CalculateDates::class.java))
        }

    }
}