package edu.ib.hellobaby

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentValues.TAG
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import edu.ib.hellobaby.fragments.SearchLine
import edu.ib.hellobaby.fragments.SearchModel
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var searchList: List<SearchModel> = ArrayList()
        val searchLine = SearchLine(searchList)


            val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
            search_view2.hasFixedSize()
            search_view2.layoutManager=LinearLayoutManager(this)
            search_view2.adapter=searchLine
        edit_search_2.addTextChangedListener(object:TextWatcher{

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val searchText : String = edit_search_2.text.toString()
                    searchInFirestore(searchText)
                }

                private fun searchInFirestore(searchText: String) {

                    firebaseFirestore.collection("week").orderBy("funfact")
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
