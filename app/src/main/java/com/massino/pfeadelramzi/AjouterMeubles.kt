package com.massino.pfeadelramzi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.massino.pfeadelramzi.models.Meuble
import kotlinx.android.synthetic.main.activity_ajouter_meubles.*

class AjouterMeubles : AppCompatActivity(){

  //  var mDatabase: DatabaseReference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_meubles)

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseref = firebaseDatabase.getReference("test1")


        button4.setOnClickListener{
            var nomUI = textView.text.toString()
            var prixUI = textView2.text.toString()
            var stockUI=  textView3.text.toString().toInt()

            if (!TextUtils.isEmpty(nomUI) || !TextUtils.isEmpty(prixUI) || !TextUtils.isEmpty(
                    stockUI.toString()
                )){
                var meuble = Meuble(R.drawable.fauteuille2,nomUI,prixUI,stockUI)

                databaseref.setValue(meuble)
            }else {
                Toast.makeText(this,"Remplissez la case manquante",Toast.LENGTH_LONG).show()
            }


            val intent5 = Intent(this, ListeMeuble3DActivity::class.java)

            startActivity(intent5)
            finish()
        }
    }
}