package com.github.notizklotz.swisswowbagger.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object InsultRepository {

    private val wowbaggerApiClient = wowbaggerApiClientInstance

    suspend fun getRandomInsult(name: String): Insult = withContext(Dispatchers.IO) {
        wowbaggerApiClient.getRandomInsult(name).let { Insult(it.id, it.text, name) }
    }

    suspend fun getInsult(id: String): Insult = withContext(Dispatchers.IO) {
        wowbaggerApiClient.getInsult(id).let { Insult(it.id, it.text, "") }
    }

}

data class Insult(val id: Long, val text: String, val name: String) {
    val websiteUrl = "$websiteBaseUrl/#$id"

    fun getAudioUrl(voice: Voice) =
        "$wowbaggerApiBaseUrl/$id?format=wav&v=undefined&names=$name&voice=$voice"
}

@Suppress("EnumEntryName")
enum class Voice(val label: String) {
    roboter("Bärner Roboter"), exilzuerchere("Zürchere im Exil"), welschi("Ä Wäutschi"), tessiner("Ä Tessiner")
}

const val websiteBaseUrl = "https://nidi3.github.io/swiss-wowbagger"