package edu.ib.hellobaby.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import edu.ib.hellobaby.R
import edu.ib.hellobaby.model.Meme
import com.squareup.picasso.Picasso

class MemeAdapter(
    private val mContext: Context,
    private val mNote: List<Meme>) : RecyclerView.Adapter<MemeAdapter.ViewHolder>() {

    private var firebaseUser: FirebaseUser? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.meme_layout, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        firebaseUser = FirebaseAuth.getInstance().currentUser

        val note = mNote[position]

        Picasso.get().load(note.getMemeimage()).into(holder.memeImage)

        //holder.memeImage.rotation = 90F
    }

    override fun getItemCount(): Int {
        return mNote.size
    }

    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        var memeImage: ImageView

        init {
            memeImage = itemView.findViewById(R.id.meme_img)
        }
    }
}