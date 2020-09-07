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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
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
           var prixm = prixaffiché.text.toString().split(" Da").joinToString("")
        // var prixm= "5"//prix meuble
            var quantm= quantité.text.toString() // quantité souhaitée
            val user = FirebaseAuth.getInstance().currentUser // pointer user
            val emailc= user!!.email // email user
            var nompersonne= user.displayName // nom user
            var date = LocalDateTime.now() // date de la commande
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            var formatted = date.format(formatter)
            Toast.makeText(this,formatted,Toast.LENGTH_LONG).show()

            val adressc = adressL.text.toString() // adresse de livraison
            formatted = formatted.replace(".","")
            if (quantm !="" && adressc != "" ){
                val setcommand = Commande(nommselected,emailc!!,nompersonne!!,quantm.toInt(),formatted,adressc,prixm.toInt())

                var databaseref = FirebaseDatabase.getInstance().getReference("commande").child(formatted)
                // pour le user
                var databaserefclient = FirebaseDatabase.getInstance().getReference(nompersonne).child(formatted)

                databaseref.setValue(setcommand)
                databaserefclient.setValue(setcommand)
            }else{
                Toast.makeText(this,"Veuillez remplir les champs vide",Toast.LENGTH_LONG).show()
            }



        }
    }




}


