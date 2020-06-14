package com.massino.pfeadelramzi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.massino.pfeadelramzi.models.Meuble
import kotlinx.android.synthetic.main.activity_liste_meuble3_d.*
import org.jetbrains.anko.toast

class ListeMeuble3DActivity : AppCompatActivity() {

    private val mNames = java.util.ArrayList<String>()
    private val mImageUrls = java.util.ArrayList<Int>()
    private val mPrix = java.util.ArrayList<String>()
    private val mDislistMeubleslistMeublespo = java.util.ArrayList<String>()

    var listMeubles = mutableListOf<Meuble>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_meuble3_d)
         listMeubles = generateList()
        buildRecyclerView()
    }

    private fun generateList(): MutableList<Meuble>{

        val list = ArrayList<Meuble>()
            val meuble = Meuble(R.drawable.fauteilgris, "Gris","25000da",1)
            val meuble1 = Meuble(R.drawable.fauteuille1, "","1000d&",1)
            val meuble2 = Meuble(R.drawable.burau, "Bureau","25000da",1)
            list.add(meuble)
        //mon_recycler.adapter?.notifyItemInserted(0)
            list.add(meuble1)
        //mon_recycler.adapter?.notifyItemInserted(0)
            list.add(meuble2)
       // mon_recycler.adapter?.notifyItemInserted(0)
        list.add(meuble)
        list.add(meuble1)
        list.add(meuble2)

        list.add(meuble)
        list.add(meuble1)
        list.add(meuble2)



        return list
    }

    fun buildRecyclerView() {
        mon_recycler.setHasFixedSize(true)
        //mon_recycler.setAdapter(mAdapter)
        mon_recycler.layoutManager = LinearLayoutManager(this)

        mon_recycler.adapter = MeubleAdapter(listMeubles.toTypedArray())
        {

            val intent3 = Intent(this, SceneformeActivity::class.java)
            intent3.putExtra("image_url",1)
            startActivity(intent3)




        }


    }



}