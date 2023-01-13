package edu.ib.hellobaby

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
public fun database(){
    val db = Firebase.firestore //ta aktywnosc i funkcja służy mi  tylko do budowy bazy jej potem nie bedzie serio
    //Pozdrawiam z tego kodu mame
    val week = hashMapOf(
        "trimester" to 3,
        "vegetable" to "Arbuz",
        "lenght" to 52.3,
        "weight" to 3500,
        "week" to 40,
        "funfact" to "Płód przychodzi na świat. Gratulacje!"
    )

// Add a new document with a generated ID
    db.collection("week")
        .document("40").set(week)
        //.add(week)
        .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${'d'}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }

}
class nieeeee : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nieeeee)

    }


}