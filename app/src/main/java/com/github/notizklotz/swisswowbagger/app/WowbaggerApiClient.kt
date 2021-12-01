package com.github.notizklotz.swisswowbagger.app

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl("https://swiss-wowbagger-ultgi7by3q-oa.a.run.app")
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

interface WowbaggerApiClient {

    @GET("/users/{user}")
    suspend fun getInsult(): String

}

object WowbaggerApi {
    val retrofitService: WowbaggerApiClient by lazy {
        retrofit.create(WowbaggerApiClient::class.java)
    }
}
