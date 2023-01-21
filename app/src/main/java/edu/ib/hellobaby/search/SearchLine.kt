package edu.ib.hellobaby.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.ib.hellobaby.R
import kotlinx.android.synthetic.main.single_search_layout.view.*

class SearchLine(var searchList: List<SearchModel>): RecyclerView.Adapter<SearchLine.SearchListVievHolder>() {

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