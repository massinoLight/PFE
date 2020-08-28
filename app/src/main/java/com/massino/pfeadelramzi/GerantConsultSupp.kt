package com.massino.pfeadelramzi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.massino.pfeadelramzi.models.Meuble
import kotlinx.android.synthetic.main.activity_gerant_consult_supp.*
import kotlinx.android.synthetic.main.activity_liste_meuble3_d.*

class GerantConsultSupp : AppCompatActivity() {
    var mdatabase : DatabaseReference?=null
    var listMeubles = listOf<Meuble>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerant_consult_supp)

        mdatabase = FirebaseDatabase.getInstance().reference.child("Meuble")
        mdatabase!!.addValueEventListener(object : ValueEventListener {
            // solution provisoir
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()&& snapshot.hasChildren()){

                    listMeubles= snapshot.children.map {it.getValue(Meuble::class.java)!! }

                }
                mon_recycler4.setHasFixedSize(true)
                mon_recycler4.layoutManager = LinearLayoutManager(this@GerantConsultSupp)
                mon_recycler4.adapter = ConsultSuppAdapter(listMeubles.toTypedArray()){
                    val intent3 = Intent(this@GerantConsultSupp,SceneformKot::class.java)
                    intent3.putExtra("image_url", it.nom)
                    startActivity(intent3)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}