package edu.ib.hellobaby.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import edu.ib.hellobaby.R
import edu.ib.hellobaby.adapter.MemeAdapter
import edu.ib.hellobaby.model.Meme

class HomeFragment : Fragment() {

    private var noteAdapter: MemeAdapter? = null
    private var noteList: MutableList<Meme>? = null

    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        var recyclerView: RecyclerView? = view.findViewById(R.id.recycler_view_home)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        if (recyclerView != null) {
            recyclerView.layoutManager = linearLayoutManager
        }

        noteList = ArrayList()
        noteAdapter = context?.let { MemeAdapter(it, noteList as ArrayList<Meme>) }
        if (recyclerView != null) {
            recyclerView.adapter = noteAdapter
        }

        retrievePosts()

        return view
    }

    private fun retrievePosts() {

        val postsRef = FirebaseDatabase.getInstance().reference.child("Notes")

        postsRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snap: DataSnapshot) {
                noteList?.clear()

                for (snapshot in snap.children) {

                    val note = snapshot.getValue(Meme::class.java)

                    if (note != null) {
                        noteList!!.add(note)
                    }

                    noteAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}