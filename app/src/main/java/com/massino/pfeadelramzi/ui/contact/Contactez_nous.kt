package com.massino.pfeadelramzi.ui.contact

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.massino.pfeadelramzi.*
import com.massino.pfeadelramzi.ui.notez.NoteznimportequoiViewModel

class Contactez_nous : Fragment() {


    private lateinit var viewModel: ContactezNousViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ContactezNousViewModel::class.java)
        val root = inflater.inflate(R.layout.contactez_nous_fragment, container, false)
        return root
    }


}