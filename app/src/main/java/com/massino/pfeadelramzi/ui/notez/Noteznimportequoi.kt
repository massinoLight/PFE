package com.massino.pfeadelramzi.ui.notez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.massino.pfeadelramzi.R
import com.massino.pfeadelramzi.models.Ratingdata
import org.jetbrains.anko.find


class Noteznimportequoi : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.noteznimportequoi_fragment, container, false)

        val ratingBar: RatingBar = root.findViewById(R.id.ratingBar)
        val setrating: ImageView = root.findViewById(R.id.setrating)
        val aviscient: EditText = root.findViewById(R.id.et_avisclient)
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val user= FirebaseAuth.getInstance().currentUser
        val utilisat = user!!.displayName
        val emmail = user.email
        setrating.setOnClickListener{
            val mdatabase = firebaseDatabase.getReference("Rating").child(utilisat!!)
            val msg= ratingBar.rating.toString()
            val avis = aviscient.text.toString()
            Toast.makeText(context,"On vous remercie !"+msg,Toast.LENGTH_LONG).show()
//            val user = FirebaseAuth.getInstance().currentUser
//            val nam= user!!.displayName
            val res = Ratingdata(utilisat,emmail!!,msg,avis)
        mdatabase!!.setValue(res)

           /* ratingBar.onRatingBarChangeListener =
                OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                    if (fromUser) mdatabase!!.setValue(rating);
                }*/
        }

        return root
    }




}