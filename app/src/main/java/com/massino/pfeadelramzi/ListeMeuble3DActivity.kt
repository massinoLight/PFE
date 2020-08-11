package com.massino.pfeadelramzi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.massino.pfeadelramzi.models.Meuble
import kotlinx.android.synthetic.main.activity_liste_meuble3_d.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
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
            val meuble = Meuble(R.drawable.fauteilgris, "Fauteuil gris","25000da",1)
            val meuble1 = Meuble(R.drawable.fauteuille1, "Fauteuil une place","1000da",1)
            val meuble2 = Meuble(R.drawable.burau, "Bureau","25000da",1)
            list.add(meuble)
        //mon_recycler.adapter?.notifyItemInserted(0)
            list.add(meuble1)
        //mon_recycler.adapter?.notifyItemInserted(0)
            list.add(meuble2)
       // mon_recycler.adapter?.notifyItemInserted(0)
        val meuble3 = Meuble(R.drawable.banc, "Banc","25000da",1)
        list.add(meuble3)
        val meuble4 = Meuble(R.drawable.table, "Table","25000da",1)
        list.add(meuble4)
        val meuble5 = Meuble(R.drawable.thor, "Thor","25000da",1)
        list.add(meuble5)
        val meuble6 = Meuble(R.drawable.fauteuille2, "Fauteuil trois places","25000da",1)
        list.add(meuble6)
        val meuble7 = Meuble(R.drawable.fauteuille3, "Fauteuil simple","25000da",1)
        list.add(meuble7)
        val meuble8 = Meuble(R.drawable.fautrouge, "Salon","25000da",1)
        list.add(meuble8)
        val meuble9 = Meuble(R.drawable.armoiremoderne, "Lit une place","25000da",1)
        list.add(meuble9)
        val meuble10= Meuble(R.drawable.lampe, "Etagère","25000da",1)
        list.add(meuble10)
        val meuble11= Meuble(R.drawable.tabledenuit, "Commande","25000da",1)
        list.add(meuble11)
        val meuble12 = Meuble(R.drawable.tableverre, "Cuisine","25000da",1)
        list.add(meuble12)

        // j'ai ajouté au hasard ken le tout pour essayer si un des models marche

        return list
    }

    fun buildRecyclerView() {
        mon_recycler.setHasFixedSize(true)
        //mon_recycler.setAdapter(mAdapter)
        mon_recycler.layoutManager = LinearLayoutManager(this)

        mon_recycler.adapter = MeubleAdapter(listMeubles.toTypedArray())
        {

            /* val intent3 = Intent(this, SceneformeActivity::class.java)
             // j'ai envoyé "nom" parceque avant il envoyait à chaque fois la valeur 1 et jamais la vrai position du click il faut implimenter une interface dans l'adapter et changer bcp de chose pour utiliser position...
             intent3.putExtra("image_url",it.nom)
             startActivity(intent3)*/
            val intent3 = Intent(this, SceneformKot::class.java)
            intent3.putExtra("image_url", it.nom)
            startActivity(intent3)





        }


    }



}