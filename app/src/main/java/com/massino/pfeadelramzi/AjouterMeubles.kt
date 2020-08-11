package com.massino.pfeadelramzi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_ajouter_meubles.*

class AjouterMeubles : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_meubles)

        button4.setOnClickListener{
            var pre = textView.text.toString()
            var deux = textView2.text.toString()
            var trois = textView3.text.toString()
            val intent5 = Intent(this, ListeMeuble3DActivity::class.java)

            startActivity(intent5)
            finish()
        }
    }
}