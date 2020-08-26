package com.massino.pfeadelramzi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.massino.pfeadelramzi.models.Commande
import kotlinx.android.synthetic.main.commande_enattente.view.*

class CmndGerantAdapter (private val exampleliste: Array<Commande>, val listener:(Commande)-> Unit):
RecyclerView.Adapter<CmndGerantAdapter.ExampleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.commande_enattente,
            parent, false)
        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleliste[position]

            holder.CGNomMeuble.text = currentItem.article
            holder.CGQuantite.text = currentItem.quantite.toString()
            holder.CGDate.text = currentItem.date
            holder.CGUtilisateur.text = currentItem.utlisateur
            holder.CGAdresseMail.text = currentItem.mailUtilisateur
            holder.CGAdresse.text = currentItem.adresse
            holder.bind(exampleliste[position],listener)




       // holder.imageView.setImageResource(currentItem.imageResource)

        //le meuble a afficher
    }
    override fun getItemCount() = exampleliste.size
    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(commande: Commande, listener: (Commande) -> Unit)= with(itemView)
        {
            android.util.Log.i("XXXX","FCT Bind ")

            //l'action a realiser lors du clic  sur un element
            setOnClickListener{(listener(commande))}
        }

        val CGNomMeuble: TextView = itemView.CGNomMeuble
        val CGQuantite: TextView = itemView.CGQuantité
        val CGDate: TextView = itemView.CGDate
        val CGUtilisateur: TextView = itemView.CGUtilisateur
        val CGAdresseMail: TextView = itemView.CGAdresseMail
        val CGAdresse: TextView = itemView.CGAdresse

    }



}