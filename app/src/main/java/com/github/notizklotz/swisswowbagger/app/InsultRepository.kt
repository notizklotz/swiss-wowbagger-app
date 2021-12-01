package com.github.notizklotz.swisswowbagger.app

object InsultRepository {

    private val wowbaggerApiClient = WowbaggerApi.retrofitService

    suspend fun getRandomInsult(): Insult = wowbaggerApiClient.getInsult()
}