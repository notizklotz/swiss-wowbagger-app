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
import com.github.notizklotz.swisswowbagger.app.data.Voice
import com.github.notizklotz.swisswowbagger.app.logError
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _insult = MutableLiveData<Insult>()
    val insult: LiveData<Insult> = _insult

    val name = MutableLiveData("")

    private val _voice = MutableLiveData(Voice.exilzuerchere)
    val voice: LiveData<Voice>
        get() = _voice

    private var fetchInsultIdlingResource: CountingIdlingResource? = null

    fun fetchInsult(insultId: String? = null) {
        fetchInsultIdlingResource?.increment()

        viewModelScope.launch {
            try {
                val fetchedInsult = if (insultId == null) {
                    InsultRepository.getRandomInsult(name.value ?: "")
                } else {
                    InsultRepository.getInsult(insultId)
                }
                _insult.value = fetchedInsult
                InsultSpeechPlayer.play(fetchedInsult.getAudioUrl(voice.value ?: Voice.exilzuerchere))
                fetchInsultIdlingResource?.decrement()
            } catch (error: Exception) {
                logError { "Could not fetch insult" to error }
            }
        }
    }

    fun setVoice(voice: Voice) {
        _voice.value = voice

        insult.value?.let { InsultSpeechPlayer.play(it.getAudioUrl(voice)) }
    }

    @VisibleForTesting
    internal fun getIdlingResource(): IdlingResource {
        if (fetchInsultIdlingResource == null) {
            fetchInsultIdlingResource = CountingIdlingResource("insultfetch")
        }
        return fetchInsultIdlingResource!!
    }

}