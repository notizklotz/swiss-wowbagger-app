package com.github.notizklotz.swisswowbagger.app.data

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.VisibleForTesting
import androidx.startup.Initializer
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

private const val wowbaggerBaseUrl = "https://swiss-wowbagger-ultgi7by3q-oa.a.run.app"

interface WowbaggerApiClient {

    suspend fun getInsult(names: List<String>): Insult

}

@Serializable
data class Insult(val id: Long, val text: String) {
    fun getAudioUrl(names: List<String>) =
        "$wowbaggerBaseUrl/$id?format=wav&v=undefined&names=${names.joinToString(" ")}"
}

class KtorWowbaggerApiClient(private val userAgentString: String): WowbaggerApiClient {

    private val client = HttpClient(CIO) {
        engine {
            requestTimeout = 5_000
        }
        install(JsonFeature)
        install(UserAgent) {
            agent = userAgentString
        }
    }

    override suspend fun getInsult(names: List<String>): Insult {
        return client.get(wowbaggerBaseUrl) {
            parameter("format", "json")
            parameter("names", names.joinToString(" "))
        }
    }
}

@VisibleForTesting
internal fun createUserAgentString(context: Context): String {
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val versionName = try {
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        "nameNotFound"
    }
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    val version = Build.VERSION.SDK_INT
    val versionRelease = Build.VERSION.RELEASE
    return "wowbagger-android/$versionName ($manufacturer; $model; SDK $version; Android $versionRelease)"
}

lateinit var wowbaggerApiClientInstance: WowbaggerApiClient

@Suppress("unused")
class WowbaggerApiClientInitializer : Initializer<WowbaggerApiClient> {
    override fun create(context: Context): WowbaggerApiClient {
        wowbaggerApiClientInstance = KtorWowbaggerApiClient(createUserAgentString(context))
        return wowbaggerApiClientInstance
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

}