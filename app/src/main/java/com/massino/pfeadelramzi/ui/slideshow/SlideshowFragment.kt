package com.massino.pfeadelramzi.ui.slideshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.massino.pfeadelramzi.MapsActivity
import com.massino.pfeadelramzi.R

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)

        val lamap: ImageView = root.findViewById(R.id.map)

         lamap.setOnClickListener {
             val intent2 = Intent(getActivity(), MapsActivity::class.java)
             startActivity(intent2)
         }

        return root
    }
}
