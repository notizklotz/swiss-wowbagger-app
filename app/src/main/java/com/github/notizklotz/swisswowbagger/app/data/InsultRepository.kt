package com.github.notizklotz.swisswowbagger.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object InsultRepository {

    private val wowbaggerApiClient = wowbaggerApiClientInstance

    suspend fun getInsult(names: List<String>): Insult = withContext(Dispatchers.IO) {
        wowbaggerApiClient.getInsult(names)
    }

}