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
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.ib.hellobaby.R
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment() {
    val db = Firebase.firestore


class SearchLine(var searchList: List<SearchModel>):RecyclerView.Adapter<SearchLine.SearchListVievHolder>() {
    class SearchListVievHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(searchModel: SearchModel) {
            itemView.SingleTextViev.text = searchModel.funfact
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListVievHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_search, parent, false)
        return SearchListVievHolder(view)
    }

    override fun onBindViewHolder(holder: SearchListVievHolder, position: Int) {
        holder.bind(searchList[position])
    }

    override fun getItemCount(): Int {

        return searchList.size
    }

}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        srch_edt_txt.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               val searchText : String = srch_edt_txt.text.toString()
                searchInFirestore(searchText)
            }


        })

    }
    private var searchList: List<SearchModel> = ArrayList()
    private val searchLine = SearchFragment.SearchLine(searchList)
    private fun searchInFirestore(searchText: String) {
        db.collection("week").orderBy("funfact")
            .startAt(searchText).endAt("$searchText\uf8ff")
            .get().addOnSuccessListener {

                    searchList=it.toObjects(SearchModel::class.java)
                    searchLine.searchList=searchList
searchLine.notifyDataSetChanged()

            }.addOnFailureListener { e ->
                Log.w(TAG, "Error", e)
            }
    }

}