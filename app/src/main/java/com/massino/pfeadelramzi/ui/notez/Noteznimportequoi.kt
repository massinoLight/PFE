package com.massino.pfeadelramzi.ui.notez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.massino.pfeadelramzi.R


class Noteznimportequoi : Fragment() {
    var mdatabase : DatabaseReference?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.noteznimportequoi_fragment, container, false)

        val ratingBar: RatingBar = root.findViewById(R.id.ratingBar)
        val setrating: ImageView = root.findViewById(R.id.setrating)

        mdatabase = FirebaseDatabase.getInstance().reference.child("Rating")
        setrating.setOnClickListener{
            val msg= ratingBar.rating.toString()
            Toast.makeText(context,"On vous remercie !",Toast.LENGTH_LONG).show()

            ratingBar.onRatingBarChangeListener =
                OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                    if (fromUser) mdatabase!!.setValue(rating);
                }
        }


//        mdatabase!!.addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//            override fun onDataChange(snapshot: DataSnapshot) {
//                TODO("Not yet implemented")
//            }
//
//        })
        return root
    }




}