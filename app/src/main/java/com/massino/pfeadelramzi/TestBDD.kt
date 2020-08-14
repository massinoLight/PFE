package com.massino.pfeadelramzi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_test_b_d_d.*

class TestBDD : AppCompatActivity() {
    var mdatabase : DatabaseReference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_b_d_d)

        var ts = test1.text.toString()

        //mdatabase = firebaseDatabase.getReference("MeubleDB")
        //    .child(nomUI)
        mdatabase = FirebaseDatabase.getInstance().reference
            .child("MeubleDB").child("Salon")

     test2.text = mdatabase!!.child("prix").toString()



    }
}