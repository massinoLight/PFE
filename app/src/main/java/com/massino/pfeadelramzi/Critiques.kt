package com.massino.pfeadelramzi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.massino.pfeadelramzi.models.Commande
import com.massino.pfeadelramzi.models.Ratingdata
import kotlinx.android.synthetic.main.activity_check_commande_gerant.*

class Critiques : AppCompatActivity() {
    var mdatabase : DatabaseReference?=null
    var listcritiqu = listOf<Ratingdata>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_critiques)

        mdatabase = FirebaseDatabase.getInstance().reference.child("Rating")
        mdatabase!!.addValueEventListener(object : ValueEventListener {
            // solution provisoir
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()&& snapshot.hasChildren()){
                    listcritiqu= snapshot.children.map {it.getValue(Ratingdata::class.java)!! }

                }
                mon_recycler2.setHasFixedSize(true)
                mon_recycler2.layoutManager = LinearLayoutManager(this@Critiques)
                mon_recycler2.adapter = CritiquesAdapter(listcritiqu.toTypedArray()){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
}