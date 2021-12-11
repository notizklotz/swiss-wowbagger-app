package com.github.notizklotz.swisswowbagger.app

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.github.notizklotz.swisswowbagger.app.components.TEST_TAG_INSULT_TARGET_NAME
import com.google.common.truth.Truth.assertWithMessage
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class PlayInsultsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private var fetchInsultIdlingResource: IdlingResource? = null
    private var playInsultIdlingResource: IdlingResource? = null

    @Test
    fun insultWithNoNameSelected() {
        // Precondition
        composeTestRule.onNodeWithTag(TEST_TAG_INSULT_TEXT).assertTextEquals("Ganz brav")

        // Execute
        composeTestRule.onNodeWithText("Beleidige!").performClick()

        // Verify
        composeTestRule.onNodeWithTag(TEST_TAG_INSULT_TEXT).assert(hasTextExactly("Ganz brav").not())

        assertWithMessage("Media player did not play")
            .that(InsultSpeechPlayer.playCount).isEqualTo(1)
    }

    @Test
    fun insultWithSelectedName() {
        // Precondition
        composeTestRule.onNodeWithTag(TEST_TAG_INSULT_TEXT).assertTextEquals("Ganz brav")

        composeTestRule.onNodeWithTag(TEST_TAG_INSULT_TARGET_NAME).performClick().performTextInput("Ädu")

        // Execute
        composeTestRule.onNodeWithText("Beleidige!").performClick()

        // Verify
        composeTestRule.onNodeWithTag(TEST_TAG_INSULT_TEXT).assertTextContains("Ädu", substring = true)

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
