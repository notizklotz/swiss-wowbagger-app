package com.github.notizklotz.swisswowbagger.app.data

object InsultRepository {

    private val wowbaggerApiClient = WowbaggerApi.retrofitService

    suspend fun getInsult(names: List<String>): Insult = wowbaggerApiClient.getInsult(names)

}