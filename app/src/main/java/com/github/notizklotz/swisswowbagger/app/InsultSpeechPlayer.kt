package com.github.notizklotz.swisswowbagger.app

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.annotation.VisibleForTesting
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import io.ktor.http.*

object InsultSpeechPlayer {

    private var mediaPlayer: MediaPlayer? = null

    private var idlingResource: CountingIdlingResource? = null

    @Suppress("ObjectPropertyName")
    private var _playCount = 0
    var playCount: Int
        get() = _playCount
        private set(value) {
            _playCount = value
        }

    fun play(url: Url) {
        idlingResource?.increment()
        playCount++

        releaseMediaPlayer()

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url.toString())
            prepareAsync()
            setOnPreparedListener { start() }
            setOnCompletionListener {
                releaseMediaPlayer()
                idlingResource?.decrement()
            }
        }
    }

    private fun releaseMediaPlayer() {
        try {
            if (mediaPlayer?.isPlaying == true) { mediaPlayer?.stop() }
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            logError { "releaseMediaPlayer: could not stop or release" to e }
        }
    }

    @VisibleForTesting
    internal fun getIdlingResource(): IdlingResource {
        if (idlingResource == null) {
            idlingResource = CountingIdlingResource("insultplay")
        }
        playCount = 0
        return idlingResource!!
    }

}