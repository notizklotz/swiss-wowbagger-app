package com.github.notizklotz.swisswowbagger.app.macrobenchmark

import android.content.Intent
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val TARGET_PACKAGE = "com.github.notizklotz.swisswowbagger.app"

@RunWith(AndroidJUnit4::class)
class StartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() {
        benchmarkRule.measureRepeated(
            packageName = TARGET_PACKAGE,
            metrics = listOf(StartupTimingMetric()),
            iterations = 3,
            startupMode = StartupMode.COLD
        ) {
            pressHome()
            val intent = Intent()
            intent.setPackage(TARGET_PACKAGE)
            intent.action = "com.github.notizklotz.swisswowbagger.app.MAIN_ACTIVITY"
            startActivityAndWait(intent)
        }
    }
}
