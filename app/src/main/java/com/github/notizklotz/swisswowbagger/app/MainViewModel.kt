package com.github.notizklotz.swisswowbagger.app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _insult = MutableLiveData<String>()
    val insult: LiveData<String> = _insult

    private val _insultAudioUrl = MutableLiveData<String>()
    val insultAudioUrl: LiveData<String> = _insultAudioUrl

    init {
        fetchInsult()
    }

    fun fetchInsult() {
        viewModelScope.launch {
            try {
                // Calling the repository is safe as it will move execution off
                // the main thread
                val randomInsult = InsultRepository.getRandomInsult()
                _insult.value = randomInsult.text
                _insultAudioUrl.value = randomInsult.getAudioUrl()
            } catch (error: Exception) {
                Log.e("Wowbagger", "Could not fetch insult", error)
            }
        }
    }

}