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
import io.ktor.http.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _insult = MutableLiveData<Insult>()
    val insult: LiveData<Insult> = _insult

    val name = MutableLiveData("")

    private val _voice = MutableLiveData(Voice.exilzuerchere)
    val voice: LiveData<Voice>
        get() = _voice

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData(false)
    val error: LiveData<Boolean>
        get() = _error

    private var fetchInsultIdlingResource: CountingIdlingResource? = null

    fun fetchInsultAndPlay(insultId: String? = null) {
        fetchInsultIdlingResource?.increment()
        _error.value = false

        viewModelScope.launch {
            try {
                _loading.value = true
                val fetchedInsult = if (insultId == null) {
                    InsultRepository.getRandomInsult(name.value ?: "")
                } else {
                    InsultRepository.getInsult(name.value ?: "", insultId)
                }
                _insult.value = fetchedInsult

                playInsult(fetchedInsult.getAudioUrl(voice.value ?: Voice.exilzuerchere))
                fetchInsultIdlingResource?.decrement()
            } catch (error: Exception) {
                _loading.value = false
                _error.value = true
                logError { "Could not fetch insult" to error }
            }
        }
    }

    fun setVoiceAndPlay(voice: Voice) {
        _voice.value = voice

        insult.value?.let {
            _loading.value = true

            playInsult(it.getAudioUrl(voice))
        }
    }

    fun setValuesAndPlay(insultId: String?, names: String?, voice: Voice?) {
        _voice.value = voice ?: Voice.exilzuerchere
        name.value = names ?: ""

        fetchInsultAndPlay(insultId)
    }

    private fun playInsult(url: Url) = InsultSpeechPlayer.play(
        url = url,
        onPrepared = { _loading.value = false },
        onError = {
            _loading.value = false
            _error.value = true
        })

    @VisibleForTesting
    internal fun getIdlingResource(): IdlingResource {
        if (fetchInsultIdlingResource == null) {
            fetchInsultIdlingResource = CountingIdlingResource("insultfetch")
        }
        return fetchInsultIdlingResource!!
    }

}