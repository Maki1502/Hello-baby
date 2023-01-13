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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.ib.hellobaby.R
import edu.ib.hellobaby.adapter.InfoAdapter
import edu.ib.hellobaby.model.Info
import kotlinx.android.synthetic.main.activity_booking.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.single_search_layout.view.*

class SearchFragment : Fragment() {
     val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var recyclerView: RecyclerView? = null
    class SearchLine(var searchList: List<SearchModel>):RecyclerView.Adapter<SearchLine.SearchListVievHolder>() {
        class SearchListVievHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(searchModel: SearchModel) {
                itemView.single_search.text = searchModel.funfact
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListVievHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.single_search_layout, parent, false)
            return SearchListVievHolder(view)
        }
        override fun onBindViewHolder(holder: SearchListVievHolder, position: Int) {
            holder.bind(searchList[position])
        }
        override fun getItemCount(): Int {
            return searchList.size
        }
    }
    private var searchList: List<SearchModel> = ArrayList()
    private val searchLine = SearchLine(searchList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        search_list.hasFixedSize()
        search_list.layoutManager=LinearLayoutManager(this.context)
        search_list.adapter=searchLine
        srch_edt_txt.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText : String = srch_edt_txt.text.toString()
                searchInFirestore(searchText) }

            private fun searchInFirestore(searchText: String) {
                firebaseFirestore.collection("Test").orderBy("test1")
                    .startAt(searchText).endAt("$searchText\uf8ff")
                    .get().addOnCompleteListener {
                        if(it.isSuccessful){
                            searchList=it.result!!.toObjects(SearchModel::class.java)
                            searchLine.searchList=searchList
                            searchLine.notifyDataSetChanged()
                        }else{
                            Log.d(TAG,"Error409")
                        }
                    }
            }
        })
    }
}