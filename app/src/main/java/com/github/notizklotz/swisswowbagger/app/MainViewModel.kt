package com.github.notizklotz.swisswowbagger.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.notizklotz.swisswowbagger.app.data.InsultRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _insult = MutableLiveData<String>()
    val insult: LiveData<String> = _insult

    private val _insultAudioUrl = MutableLiveData<String>()
    val insultAudioUrl: LiveData<String> = _insultAudioUrl

    val name = MutableLiveData("")

    fun fetchInsult() {
        viewModelScope.launch {
            try {
                // Calling the repository is safe as it will move execution off
                // the main thread
                val names = listOfNotNull(name.value)
                val randomInsult = InsultRepository.getInsult(names)
                _insult.value = randomInsult.text
                _insultAudioUrl.value = randomInsult.getAudioUrl(names)
            } catch (error: Exception) {
                logError { "Could not fetch insult" to error }
            }
        }
    }

}