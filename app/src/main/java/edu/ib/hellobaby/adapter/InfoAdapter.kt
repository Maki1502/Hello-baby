package edu.ib.hellobaby.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import edu.ib.hellobaby.R
import edu.ib.hellobaby.model.Info

class InfoAdapter(
    private var mContext: Context, private var mInfo: List<Info>,
    private var isFragment: Boolean = false
) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.info_item_layout, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val info = mInfo[position]
        holder.infoTxt.text = info.getInfotxt()
    }

    override fun getItemCount(): Int {
        return mInfo.size
    }

    class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        var infoTxt: TextView = itemView.findViewById(R.id.info_search_txt)

    }
}