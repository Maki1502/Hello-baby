package edu.ib.hellobaby.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import edu.ib.hellobaby.R
import edu.ib.hellobaby.SignInActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_options.*
import kotlinx.android.synthetic.main.fragment_options.view.*

class OptionsFragment : Fragment() {

    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_options, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        view.logout_profile_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(context, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        apply_btn.setOnClickListener {
            val zaimki = zaimki_edtxt.text.toString()
            val sherPref = "zaimki"

            val pref = this.activity?.getSharedPreferences(sherPref, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor? = pref?.edit()

            if (editor != null) {
                editor.putString("pronouns", zaimki)
                editor.apply()
                editor.commit()
            }
        }

        return view
    }
}