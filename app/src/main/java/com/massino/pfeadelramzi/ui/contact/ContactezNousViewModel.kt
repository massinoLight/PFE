package com.massino.pfeadelramzi.ui.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactezNousViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Contact us Fragment"
    }
    val text: LiveData<String> = _text
}