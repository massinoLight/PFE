package com.massino.pfeadelramzi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_test_b_d_d.*

class TestBDD : AppCompatActivity() {
    var mdatabase : DatabaseReference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_b_d_d)

        mdatabase = FirebaseDatabase.getInstance().reference
            .child("MeubleDB").child("Salon")
        // salon je vais mettre variable de nom du meuble choisi
        mdatabase!!.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var prix = snapshot.child("prix").value
                test2.text= prix.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })




    }

}