package com.github.notizklotz.swisswowbagger.app.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import com.github.notizklotz.swisswowbagger.app.InsultSpeechPlayer
import com.github.notizklotz.swisswowbagger.app.data.Insult
import com.github.notizklotz.swisswowbagger.app.data.InsultRepository
import com.github.notizklotz.swisswowbagger.app.logError
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _insult = MutableLiveData<Insult>()
    val insult: LiveData<Insult> = _insult

    val name = MutableLiveData("")

    private var fetchInsultIdlingResource: CountingIdlingResource? = null

    fun fetchInsult() {
        fetchInsultIdlingResource?.increment()

        viewModelScope.launch {
            try {
                val fetchedInsult = InsultRepository.getInsult(name.value ?: "")
                _insult.value = fetchedInsult
                InsultSpeechPlayer.play(fetchedInsult.audioUrl)
                fetchInsultIdlingResource?.decrement()
            } catch (error: Exception) {
                logError { "Could not fetch insult" to error }
            }
        }
    }

    @VisibleForTesting
    internal fun getIdlingResource(): IdlingResource {
        if (fetchInsultIdlingResource == null) {
            fetchInsultIdlingResource = CountingIdlingResource("insultfetch")
        }
        return fetchInsultIdlingResource!!
    }

}