package com.github.notizklotz.swisswowbagger.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object InsultRepository {

    private val wowbaggerApiClient = wowbaggerApiClientInstance

    suspend fun getInsult(name: String): Insult = withContext(Dispatchers.IO) {
        wowbaggerApiClient.getInsult(name).let { Insult(it.id, it.text, name) }
    }

}

data class Insult(val id: Long, val text: String, val name: String) {
    val audioUrl = "$wowbaggerBaseUrl/$id?format=wav&v=undefined&names=$name"
}