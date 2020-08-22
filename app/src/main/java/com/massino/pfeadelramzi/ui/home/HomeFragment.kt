package com.massino.pfeadelramzi.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.massino.pfeadelramzi.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val lesmeubles3d: Button = root.findViewById(R.id.button2)





        val passs:Button=root.findViewById(R.id.bupasscomd)
        lesmeubles3d.setOnClickListener {
            val intent2 = Intent(getActivity(), ListeMeuble3DActivity::class.java)
            startActivity(intent2)
        }
        // passer une commande
        passs.setOnClickListener{
            val intentcom= Intent(getActivity(), FaireCommandeActivity::class.java)
            startActivity(intentcom)
        }


        return root
    }
}
