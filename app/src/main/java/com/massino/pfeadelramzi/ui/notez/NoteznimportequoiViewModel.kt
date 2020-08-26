package com.massino.pfeadelramzi.ui.notez

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteznimportequoiViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Rate us Fragment"
    }
    val text: LiveData<String> = _text
}