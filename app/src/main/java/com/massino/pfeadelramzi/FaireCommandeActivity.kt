package com.massino.pfeadelramzi

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.massino.pfeadelramzi.models.Commande
import com.massino.pfeadelramzi.models.Meuble
import kotlinx.android.synthetic.main.ui_faire_commande.*
import org.jetbrains.anko.sp
import java.time.LocalDate
import java.util.*
import kotlin.concurrent.thread

class FaireCommandeActivity : AppCompatActivity() {
    var fdatabase: DatabaseReference?=null
    var listo = listOf("Fauteuil Gris","Fauteuil une place",
        "Bureau","Banc","Table","Fauteuil trois places",
    "Fauteuil simple","Salon","Etagère","Commande","Cuisine")

    @RequiresApi(Build.VERSION_CODES.O)
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
                fdatabase = FirebaseDatabase.getInstance().reference.child("Meuble").child(listo[position])
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
        passercom.setOnClickListener{
            var nommselected= spinner.selectedItem.toString()
          //  var prixm = prixaffiché.text.toString().split(" Da").toString()//prix meuble
           var prixm= "5"
            var quantm= quantité.text.toString() // quantité souhaitée
            val user = FirebaseAuth.getInstance().currentUser // pointer user
            val emailc= user!!.email // email user
            var nompersonne= user.displayName // nom user
            var date = LocalDate.now() // date de la commande
            val adressc:String="lol" // adresse de l'user

            val setcommand = Commande(emailc!!,nompersonne!!,quantm.toInt(),date.toString(),adressc,prixm.toInt())

            var databaseref = FirebaseDatabase.getInstance().getReference("commande")
            databaseref.setValue(setcommand)


        }
    }




}


