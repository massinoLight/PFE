package com.massino.pfeadelramzi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.massino.pfeadelramzi.models.Ratingdata
import kotlinx.android.synthetic.main.critiques_card.view.*


class CritiquesAdapter (private val exampleliste: Array<Ratingdata>, val listener:(Ratingdata)-> Unit):
    RecyclerView.Adapter<CritiquesAdapter.ExampleViewHolder>(){
    var mdatabase : DatabaseReference?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.critiques_card,
            parent, false)
        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleliste[position]

        holder.utilNam.text = "E-mail: "+currentItem.mail
        holder.utilMail.text = "Utilisateur: "+currentItem.util
        holder.avis.text = currentItem.avis
        //holder.ratingstars.numStars(ratingValue)
        holder.ratingstars.rating= currentItem.note.toFloat()

        holder.bind(exampleliste[position],listener)


    }
    override fun getItemCount() = exampleliste.size
    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(critique: Ratingdata, listener: (Ratingdata) -> Unit)= with(itemView)
        {
            android.util.Log.i("XXXX","FCT Bind ")

            //l'action a realiser lors du clic  sur un element
            setOnClickListener{(listener(critique))}

        }

        val utilNam: TextView = itemView.critiUtilisateur
        val utilMail: TextView = itemView.critiqMail
        val avis: TextView = itemView.critiqueavis
        val ratingstars: RatingBar = itemView.critiquRating


    }



}