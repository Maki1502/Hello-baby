package edu.ib.hellobaby.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.ib.hellobaby.BookingActivity
import edu.ib.hellobaby.CalculateDates
import edu.ib.hellobaby.R
import edu.ib.hellobaby.SignInActivity
import kotlinx.android.synthetic.main.fragment_date.view.*

class DateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_date, container, false)

        view.calc_btn.setOnClickListener {
            val intent = Intent(context, CalculateDates::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        view.book_btn.setOnClickListener {
            val intent = Intent(context, BookingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        return view
    }
}