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
    val audioUrl = "$wowbaggerApiBaseUrl/$id?format=wav&v=undefined&names=$name"
    val websiteUrl = "$websiteBaseUrl/#$id"
}

const val websiteBaseUrl = "https://nidi3.github.io/swiss-wowbagger"