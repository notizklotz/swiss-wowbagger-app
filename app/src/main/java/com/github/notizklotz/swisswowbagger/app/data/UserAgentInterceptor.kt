package com.github.notizklotz.swisswowbagger.app.data

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.VisibleForTesting
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Adds app version and android runtime info the request user agent header.
 *
 * Inspired by [Cooper](https://github.com/danielceinos/Cooper)
 */
class UserAgentInterceptor(context: Context) : Interceptor {

    @VisibleForTesting
    internal val userAgent = run {
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
        "wowbagger-android/$versionName ($manufacturer; $model; SDK $version; Android $versionRelease)"
    }

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(chain.request().newBuilder().header("User-Agent", userAgent).build())

}