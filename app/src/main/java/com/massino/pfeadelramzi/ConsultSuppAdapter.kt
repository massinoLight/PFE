package com.massino.pfeadelramzi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.massino.pfeadelramzi.models.Meuble
import kotlinx.android.synthetic.main.gerantconsult_supp_item.view.*
import kotlinx.android.synthetic.main.meuble_item.view.image_view
import kotlinx.android.synthetic.main.meuble_item.view.nomMeuble
import kotlinx.android.synthetic.main.meuble_item.view.prix
import kotlinx.android.synthetic.main.meuble_item.view.stock


class ConsultSuppAdapter(private val exampleList: Array<Meuble>, val listener: (Meuble)-> Unit) :
    RecyclerView.Adapter<ConsultSuppAdapter.ExampleViewHolder>() {

    var mdatabase : DatabaseReference?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gerantconsult_supp_item,
            parent, false)
        return ExampleViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.textViewNomMeuble.text = currentItem.nom
        holder.textViewPrix.text = currentItem.prix.toString()
        holder.textViewStock.text = currentItem.stock.toString()
        holder.imageView.setImageResource(currentItem.imageResource)

        //le meuble a afficher
        holder.bind(exampleList[position],listener)
    }
    override fun getItemCount() = exampleList.size
    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(meuble: Meuble, listener: (Meuble) -> Unit)= with(itemView)
        {

            setOnClickListener{(listener(meuble))}
            imagb.setOnClickListener{
                val item = exampleList[adapterPosition]

                mdatabase = FirebaseDatabase.getInstance().reference.child("Meuble").child(item.nom)
                mdatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
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
        val imagb : ImageButton = itemView.imageButtonDel
        val imageView: ImageView = itemView.image_view
        val textViewNomMeuble: TextView = itemView.nomMeuble
        val textViewStock: TextView = itemView.stock
        val textViewPrix: TextView = itemView.prix
        //l'action a realiser lors du clic  sur un element

    }



}