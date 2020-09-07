package com.massino.pfeadelramzi.ui.Panierfrgt

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.massino.pfeadelramzi.MainActivity
import com.massino.pfeadelramzi.Panier

import com.massino.pfeadelramzi.R

class Panierfrgt : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.panierfrgt_fragment, container, false)
        val panierbu: ImageView = root.findViewById(R.id.buPanier)

        panierbu.setOnClickListener {
            val intent2 = Intent(getActivity(), Panier::class.java)

            startActivity(intent2)
        }
        return root
    }

}