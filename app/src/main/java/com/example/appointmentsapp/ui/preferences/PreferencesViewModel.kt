package com.example.appointmentsapp.ui.preferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appointmentsapp.util.PreferenceHelper.defaultPrefs


class PreferencesViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is preferences Fragment"
    }
    val text: LiveData<String> = _text
}