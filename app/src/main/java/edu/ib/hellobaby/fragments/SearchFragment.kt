package edu.ib.hellobaby.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    private fun searchInfo(input: String): DocumentReference {
       val data=input
        val db = Firebase.firestore
        val test= db.collection("week").document(data)
        test.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        return test
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