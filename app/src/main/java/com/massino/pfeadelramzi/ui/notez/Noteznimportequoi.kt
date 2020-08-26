package com.massino.pfeadelramzi.ui.notez

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.massino.pfeadelramzi.*

class Noteznimportequoi : Fragment() {

    private lateinit var viewModel: NoteznimportequoiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(NoteznimportequoiViewModel::class.java)
        val root = inflater.inflate(R.layout.noteznimportequoi_fragment, container, false)
        return root
    }




}