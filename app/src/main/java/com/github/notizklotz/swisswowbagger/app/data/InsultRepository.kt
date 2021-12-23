package com.github.notizklotz.swisswowbagger.app.data

import com.github.notizklotz.swisswowbagger.app.getApiBaseUrl
import com.github.notizklotz.swisswowbagger.app.getWebsiteBaseUrl
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object InsultRepository {

    private val wowbaggerApiClient = wowbaggerApiClientInstance

    suspend fun getRandomInsult(name: String): Insult = withContext(Dispatchers.IO) {
        name.trim()
            .let { trimmedName ->
                wowbaggerApiClient.getRandomInsult(trimmedName)
                    .let { Insult(it.id, it.text, trimmedName) }
            }
    }

    suspend fun getInsult(name: String, id: String): Insult = withContext(Dispatchers.IO) {
        wowbaggerApiClient.getInsult(name, id).let { Insult(it.id, it.text, name) }
    }

}

data class Insult(val id: Long, val text: String, val name: String) {

    fun getWebsiteUrl(voice: Voice): Url = URLBuilder(getWebsiteBaseUrl())
        .applyCommon(voice)
        .apply {
            fragment = id.toString()
        }
        .build()

    fun getAudioUrl(voice: Voice): Url = URLBuilder(getApiBaseUrl())
        .applyCommon(voice)
        .apply {
            parameters["format"] = "wav"
            pathComponents(id.toString())
        }
        .build()

    private fun URLBuilder.applyCommon(voice: Voice) = apply {
        parameters["v"] = "undefined"
        parameters["names"] = name
        parameters["voice"] = voice.name
    }

    companion object {
        fun createUrl(id: String, name: String): Url = URLBuilder(getApiBaseUrl()).apply {
            parameters["v"] = "undefined"
            parameters["names"] = name
            parameters["format"] = "json"
            pathComponents(id)
        }.build()
    }

}

@Suppress("EnumEntryName")
enum class Voice(val label: String) {
    roboter("Bärner Roboter"), exilzuerchere("Zürchere im Exil"), welschi("Ä Wäutschi"), tessiner("Ä Tessiner")
}