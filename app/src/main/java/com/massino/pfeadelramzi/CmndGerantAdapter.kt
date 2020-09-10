package com.massino.pfeadelramzi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.massino.pfeadelramzi.models.Commande
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.commande_enattente.view.*

class CmndGerantAdapter (private val exampleliste: Array<Commande>, val listener:(Commande)-> Unit):
RecyclerView.Adapter<CmndGerantAdapter.ExampleViewHolder>(){
    var mdatabase : DatabaseReference?=null
    var mdatabase2 : DatabaseReference?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.commande_enattente,
            parent, false)
        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleliste[position]

            holder.CGNomMeuble.text = currentItem.article
            holder.CGQuantite.text = "Quantité: "+currentItem.quantite.toString()
            holder.CGDate.text = currentItem.date
            holder.CGUtilisateur.text = currentItem.utlisateur
            holder.CGAdresseMail.text = currentItem.mailUtilisateur
            holder.CGAdresse.text = "Adresse: "+currentItem.adresse
            holder.imag.setImageResource(getImRess(currentItem.article))


            holder.bind(exampleliste[position],listener)




       // holder.imageView.setImageResource(currentItem.imageResource)

        //le meuble a afficher
    }
    override fun getItemCount() = exampleliste.size
    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(commande: Commande, listener: (Commande) -> Unit)= with(itemView)
        {
            android.util.Log.i("XXXX","FCT Bind ")

            //l'action a realiser lors du clic  sur un element
            setOnClickListener{(listener(commande))}
            dele.setOnClickListener{
                val item = exampleliste[adapterPosition]
                // enlever les données dans le noeud currentuser.nomutilisateur
                mdatabase = FirebaseDatabase.getInstance().reference.child(item.utlisateur).child(item.date)
                //  .removeValue().addOnCompleteListener(object : OnCompleteListener
                mdatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                        notifyItemRemoved(adapterPosition)
                    }

                })
                // enlever les données dans le noeud commande
                mdatabase2= FirebaseDatabase.getInstance().reference.child("commande").child(item.date)
                //  .removeValue().addOnCompleteListener(object : OnCompleteListener
                mdatabase2!!.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                        notifyItemRemoved(adapterPosition)
                    }

                })
            }
        }

        val dele: ImageButton = itemView.BUDELETE
        val CGNomMeuble: TextView = itemView.CGNomMeuble
        val CGQuantite: TextView = itemView.CGQuantité
        val CGDate: TextView = itemView.CGDate
        val CGUtilisateur: TextView = itemView.CGUtilisateur
        val CGAdresseMail: TextView = itemView.CGAdresseMail
        val CGAdresse: TextView = itemView.CGAdresse
        var imag: ImageView = itemView.meubleid

    }
    private fun getImRess (meub_name:String):Int{
        var i:Int
        when (meub_name) {
            "Fauteuil Gris" -> {
                i= R.drawable.fauteilgris
            }
            "Banc" -> {
                i= R.drawable.banc
            }
            "Bureau" -> {
                i= R.drawable.burau
            }
            "Fauteuil une place" -> {
                i= R.drawable.fauteuille3
            }
            "Fauteuil trois places" -> {
                i= R.drawable.fauteuille1

            }
            "Table" -> {
                i= R.drawable.table

            }
            "Thor" -> {
                i= R.drawable.thor

            }
            "Fauteuil simple" -> {
                i= R.drawable.thor
            }
            "Salon" -> {
                i= R.drawable.salon_im
            }
            "Lit une place" -> {
                i= R.drawable.thor
            }
            "Etagère" -> {
                i= R.drawable.etag_im
            }
            "Commande" -> {
                i= R.drawable.thor
            }
            "Cuisine" -> {
                i= R.drawable.thor
            }

            else -> i= R.drawable.thor
        }
        return i
    }



}