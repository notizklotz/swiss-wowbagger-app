package com.github.notizklotz.swisswowbagger.app

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log

object InsultSpeechPlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun play(url: String) {
        releaseMediaPlayer()

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { start() }
            setOnCompletionListener {
                releaseMediaPlayer()
            }
        }
    }

    fun releaseMediaPlayer() {
        try {
            if (mediaPlayer?.isPlaying == true) { mediaPlayer?.stop() }
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            Log.d("wowbagger", "releaseMediaPlayer: could not stop or release", e)
        }
    }
}