package com.massino.pfeadelramzi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.massino.pfeadelramzi.models.Meuble
import kotlinx.android.synthetic.main.meuble_item.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*






class MeubleAdapter(private val exampleList: Array<Meuble>,val listener: (Meuble)-> Unit) :
    RecyclerView.Adapter<MeubleAdapter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.meuble_item,
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
    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(meuble:Meuble,listener: (Meuble) -> Unit)= with(itemView)
        {
            android.util.Log.i("XXXX","FCT Bind ")

            //l'action a realiser lors du clic  sur un element
            setOnClickListener{(listener(meuble))}
        }

        val imageView: ImageView = itemView.image_view
        val textViewNomMeuble: TextView = itemView.nomMeuble
        val textViewStock: TextView = itemView.stock
        val textViewPrix: TextView = itemView.prix
        //l'action a realiser lors du clic  sur un element

    }


}