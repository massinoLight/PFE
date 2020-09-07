package com.massino.pfeadelramzi



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.massino.pfeadelramzi.models.Commande
import kotlinx.android.synthetic.main.commande_enattente.view.*


class PanierAdapter (private val exampleliste: Array<Commande>, val listener:(Commande)-> Unit):
    RecyclerView.Adapter<PanierAdapter.ExampleViewHolder>(){

    var mdatabase : DatabaseReference?=null
    var mdatabase2 : DatabaseReference?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.commande_enattente,
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
                mdatabase!!.addListenerForSingleValueEvent(object : ValueEventListener{
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
                mdatabase2!!.addListenerForSingleValueEvent(object : ValueEventListener{
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
        val dele:ImageButton = itemView.BUDELETE
        val CGNomMeuble: TextView = itemView.CGNomMeuble
        val CGQuantite: TextView = itemView.CGQuantité
        val CGDate: TextView = itemView.CGDate
        val CGUtilisateur: TextView = itemView.CGUtilisateur
        val CGAdresseMail: TextView = itemView.CGAdresseMail
        val CGAdresse: TextView = itemView.CGAdresse

    }
}