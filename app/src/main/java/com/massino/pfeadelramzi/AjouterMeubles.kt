package com.massino.pfeadelramzi

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.massino.pfeadelramzi.models.Meuble
import kotlinx.android.synthetic.main.activity_ajouter_meubles.*

class AjouterMeubles : AppCompatActivity(), AdapterView.OnItemSelectedListener{

  //  var mDatabase: DatabaseReference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_meubles)

        var firebaseDatabase = FirebaseDatabase.getInstance()
       // var databaseref = firebaseDatabase.getReference("MeubleDB").push()
        // nom meuble spinner code:
      val spinner: Spinner = spinner_nom
      ArrayAdapter.createFromResource(
          this,R.array.Nom_Meuble,android.R.layout.simple_spinner_item
      ).also { adapter ->
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
          spinner.adapter=adapter
      }
      spinner.onItemSelectedListener



        button4.setOnClickListener{
            var nomUI = spinner.selectedItem.toString()
            var prixUI = textView2.text.toString().toInt()
            var stockUI=  textView3.text.toString().toInt()

          //  var databaseref = firebaseDatabase.getReference("MeubleDB").child(nomUI)
            var databaseref = firebaseDatabase.getReference(nomUI)

            if ( nomUI != null || !TextUtils.isEmpty(prixUI.toString()) || !TextUtils.isEmpty(stockUI.toString())){
                var meuble = Meuble(R.drawable.fauteuille2,nomUI,prixUI,stockUI)

                databaseref.setValue(meuble)
            }else {
                Toast.makeText(this,"Remplissez la case manquante",Toast.LENGTH_LONG).show()
            }


            val intent5 = Intent(this, ListeMeuble3DActivity::class.java)
            //intent5.putExtra("nommeuble",nomUI)
            startActivity(intent5)

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
       parent?.getItemAtPosition(position)
    }

}