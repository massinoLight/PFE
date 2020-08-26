package com.massino.pfeadelramzi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.massino.pfeadelramzi.models.Commande
import kotlinx.android.synthetic.main.activity_check_commande_gerant.*

class Panier : AppCompatActivity() {


    var mdatabase : DatabaseReference?=null
    var listCommanCl = listOf<Commande>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panier)
        val user = FirebaseAuth.getInstance().currentUser!!.displayName!!

        mdatabase = FirebaseDatabase.getInstance().reference.child(user)
        mdatabase!!.addValueEventListener(object : ValueEventListener {
            // solution provisoir
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()&& snapshot.hasChildren()){
                    listCommanCl= snapshot.children.map {it.getValue(Commande::class.java)!! }

                }
                mon_recycler2.setHasFixedSize(true)
                mon_recycler2.layoutManager = LinearLayoutManager(this@Panier)
                mon_recycler2.adapter = PanierAdapter(listCommanCl.toTypedArray()){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}