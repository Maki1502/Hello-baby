package edu.ib.hellobaby.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import edu.ib.hellobaby.R
import edu.ib.hellobaby.adapter.InfoAdapter
import edu.ib.hellobaby.model.Info
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var infoAdapter: InfoAdapter? = null
    private var mInfo: MutableList<Info>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.rv_search)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        mInfo = ArrayList()
        infoAdapter = context?.let { InfoAdapter(it, mInfo as ArrayList<Info>, true) }
        recyclerView?.adapter = infoAdapter

        view.srch_edt_txt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (view.srch_edt_txt.text.toString() != "") {
                    recyclerView?.visibility = View.VISIBLE
                    retrieveInfo()
                    searchInfo(p0.toString().lowercase())
                }
            }
        })

        return view
    }

    private fun searchInfo(input: String) {

        val query = FirebaseDatabase.getInstance().reference
            .child("Plants")
            .orderByChild("infoname")
            .startAt(input)
            .endAt(input + "\uf8ff")

        query.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                mInfo?.clear()
                for (snapshot in dataSnapshot.children) {
                    val info = snapshot.getValue(Info::class.java)
                    if (info != null) {
                        mInfo?.add(info)
                    }
                }

                infoAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) { }

        })

    }
    private fun retrieveInfo() {
        val usersRef = FirebaseDatabase.getInstance().reference.child("Plants")
        usersRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (view?.srch_edt_txt?.text.toString() == "") {
                    mInfo?.clear()

                    for (snapshot in dataSnapshot.children) {
                        val info = snapshot.getValue(Info::class.java)
                        if (info != null) {
                            mInfo?.add(info)
                        }
                    }

                    infoAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) { }

        })
    }

}