package com.github.notizklotz.swisswowbagger.app.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserAgentInterceptorTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    @Ignore("Must find a way to shadow the values")
    fun testUserAgentString() {
        val userAgentInterceptor = UserAgentInterceptor(context)

        assertThat(userAgentInterceptor.userAgent)
            .isEqualTo("wowbagger-android/0.4.0 (robolectric; robolectric; SDK 31; Android 12)")
    }
}