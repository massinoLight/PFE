package com.massino.pfeadelramzi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import com.google.firebase.database.*
import com.massino.pfeadelramzi.models.Meuble
import kotlinx.android.synthetic.main.ui_faire_commande.*
import org.jetbrains.anko.sp

class FaireCommandeActivity : AppCompatActivity() {
    var fdatabase: DatabaseReference?=null
    var listo = listOf("Fauteuil Gris","Fauteuil une place",
        "Bureau","Banc","Table","Fauteuil trois places",
    "Fauteuil simple","Salon","Etagère","Commande","Cuisine")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_faire_commande)

        val spinner: Spinner = spinner2_nom
        ArrayAdapter.createFromResource(
            this,R.array.Nom_Meuble,android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter=adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                Toast.makeText(this@FaireCommandeActivity,listo[position], Toast.LENGTH_LONG).show()
                fdatabase = FirebaseDatabase.getInstance().reference.child(listo[position])
                fdatabase!!.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }
                    @SuppressLint("SetTextI18n")
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()&& snapshot.hasChildren()){
                          prixaffiché.text= snapshot.child("prix").value.toString()+" Da"
                          quantité.hint=  snapshot.child("stock").value.toString()
                        }else{
                            prixaffiché.text= "Article Non Disponnible"
                            quantité.hint= "0"
                        }
                    }

                })
            }

        }
        

    }



}


