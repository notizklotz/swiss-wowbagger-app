package com.github.notizklotz.swisswowbagger.app.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


const val wowbaggerBaseUrl = "https://swiss-wowbagger-ultgi7by3q-oa.a.run.app"
private val retrofit = Retrofit.Builder()
    .baseUrl(wowbaggerBaseUrl)
    .client(OkHttpClient.Builder().callTimeout(5, TimeUnit.SECONDS).build())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface WowbaggerApiClient {

    @GET("/?format=json")
    @Headers("Accept: application/json")
    suspend fun getInsult(@Query("names") names: List<String>): Insult

}

data class Insult(val id: String, val text: String) {
    fun getAudioUrl(names: List<String>) = "$wowbaggerBaseUrl/$id?format=wav&v=undefined&names=${names.joinToString(" ")}"
}

object WowbaggerApi {
    val retrofitService: WowbaggerApiClient by lazy {
        retrofit.create(WowbaggerApiClient::class.java)
    }
}
