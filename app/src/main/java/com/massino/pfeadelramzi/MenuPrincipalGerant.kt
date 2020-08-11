package com.massino.pfeadelramzi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class MenuPrincipalGerant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal_gerant)
        var firebaseDatabas= FirebaseDatabase.getInstance()
        var databaseRef = firebaseDatabas.reference

       // databaseRef.

    }

    fun ajouterMeuble(view: View) {
       // R.id.buAjouter
        val intent5 = Intent(this, AjouterMeubles::class.java)
        startActivity(intent5)
        finish()
    }

    fun RepCritiques(view: View) {
       // R.id.buCritiques

           val intent6= Intent(this,Critiques::class.java)
           startActivity(intent6)
           finish()


    }
}