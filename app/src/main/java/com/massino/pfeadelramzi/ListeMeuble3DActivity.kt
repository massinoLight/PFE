package com.massino.pfeadelramzi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.massino.pfeadelramzi.models.Meuble
import kotlinx.android.synthetic.main.activity_liste_meuble3_d.*


class ListeMeuble3DActivity : AppCompatActivity() {

    var mdatabase : DatabaseReference?=null
  //  var listMeubles = mutableListOf<Meuble>()
    var listMeubles = listOf<Meuble>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_meuble3_d)
        mdatabase = FirebaseDatabase.getInstance().reference
        mdatabase!!.addValueEventListener(object : ValueEventListener {
               // solution provisoir
               override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()&& snapshot.hasChildren()){
                        listMeubles= snapshot.children.map {it.getValue(Meuble::class.java)!! }

                    }
                   mon_recycler.setHasFixedSize(true)
                   mon_recycler.layoutManager = LinearLayoutManager(this@ListeMeuble3DActivity)
                   mon_recycler.adapter = MeubleAdapter(listMeubles.toTypedArray()){
                       val intent3 = Intent(this@ListeMeuble3DActivity,SceneformKot::class.java)
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