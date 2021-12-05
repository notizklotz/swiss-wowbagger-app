package com.github.notizklotz.swisswowbagger.app

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth.assertWithMessage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.After
import org.junit.Before

@RunWith(AndroidJUnit4::class)
@LargeTest
class PlayInsultsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private var fetchInsultIdlingResource: IdlingResource? = null
    private var playInsultIdlingResource: IdlingResource? = null

    @Test
    fun clickOnInsult() {
        // Precondition
        composeTestRule.onNodeWithText("Ganz brav").assertExists()

        // Execute
        composeTestRule.onNodeWithText("Beleidige!").performClick()

        // Verify
        composeTestRule.onNodeWithText("Ganz brav").assertDoesNotExist()

        assertWithMessage("Media player did not play")
            .that(InsultSpeechPlayer.playCount).isEqualTo(1)
    }

    @Before
    fun registerIdlingResource() {
        fetchInsultIdlingResource = composeTestRule.activity.getIdlingResource()
        playInsultIdlingResource = InsultSpeechPlayer.getIdlingResource()
        IdlingRegistry.getInstance().register(fetchInsultIdlingResource, playInsultIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        listOfNotNull(fetchInsultIdlingResource, playInsultIdlingResource).toTypedArray().let {
            IdlingRegistry.getInstance().unregister(*it)
        }
    }
}
