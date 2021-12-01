package com.github.notizklotz.swisswowbagger.app

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

const val wowbaggerBaseUrl = "https://swiss-wowbagger-ultgi7by3q-oa.a.run.app"
private val retrofit = Retrofit.Builder()
    .baseUrl(wowbaggerBaseUrl)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface WowbaggerApiClient {

    @GET("/?format=json")
    @Headers("Accept: application/json")
    suspend fun getInsult(): Insult

}

data class Insult(val id: String, val text: String) {
    fun getAudioUrl() = "$wowbaggerBaseUrl/$id?format=wav"
}

object WowbaggerApi {
    val retrofitService: WowbaggerApiClient by lazy {
        retrofit.create(WowbaggerApiClient::class.java)
    }
}
