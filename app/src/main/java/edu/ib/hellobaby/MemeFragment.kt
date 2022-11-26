package edu.ib.hellobaby

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import kotlinx.android.synthetic.main.fragment_meme.*
import kotlinx.android.synthetic.main.fragment_meme.view.*
import java.util.*


class MemeFragment : Fragment(R.layout.fragment_meme) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_meme, container, false)

        return view
    }
}