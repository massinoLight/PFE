package com.massino.pfeadelramzi.ui.home

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.massino.pfeadelramzi.ListeMeuble3DActivity
import com.massino.pfeadelramzi.MainActivity
import com.massino.pfeadelramzi.R

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

        lesmeubles3d.setOnClickListener {
            val intent2 = Intent(getActivity(), ListeMeuble3DActivity::class.java)
            startActivity(intent2)
        }


        return root
    }
}
