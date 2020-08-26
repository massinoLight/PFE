package com.massino.pfeadelramzi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_menu_principal_gerant.*
import java.lang.Exception

class MenuPrincipalGerant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal_gerant)
        var firebaseDatabas= FirebaseDatabase.getInstance()
        var databaseRef = firebaseDatabas.reference

       // databaseRef.
button6.setOnClickListener{
    val intent55=Intent(this,Panier::class.java)
    startActivity(intent55)
}
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

    fun checkCommande(view: View) {
        val intent6= Intent(this,CheckCommandeGerant::class.java)
        startActivity(intent6)
        finish()
    }

}