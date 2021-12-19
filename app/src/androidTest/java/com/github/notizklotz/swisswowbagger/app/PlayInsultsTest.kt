package com.github.notizklotz.swisswowbagger.app

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.github.notizklotz.swisswowbagger.app.main.MainActivity
import com.github.notizklotz.swisswowbagger.app.main.TEST_TAG_INSULT_BUTTON
import com.github.notizklotz.swisswowbagger.app.main.TEST_TAG_INSULT_TARGET_NAME
import com.github.notizklotz.swisswowbagger.app.main.TEST_TAG_INSULT_TEXT
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
        val playCountBefore = InsultSpeechPlayer.playCount

        // Precondition
        composeTestRule.onNodeWithTag(TEST_TAG_INSULT_TEXT).assertTextEquals("Ganz brav")

        // Execute
        composeTestRule.onNodeWithTag(TEST_TAG_INSULT_BUTTON).performClick()

        composeTestRule.waitForIdle()

        // Verify
        composeTestRule.onNodeWithTag(TEST_TAG_INSULT_TEXT).assert(hasTextExactly("Ganz brav").not()).printToString()

        assertWithMessage("Media player did not play")
            .that(InsultSpeechPlayer.playCount).isEqualTo(playCountBefore + 1)
    }

    @Test
    fun insultWithSelectedName() {
        val playCountBefore = InsultSpeechPlayer.playCount

        // Precondition
        composeTestRule.onNodeWithTag(TEST_TAG_INSULT_TEXT).assertTextEquals("Ganz brav")

        composeTestRule.onNodeWithTag(TEST_TAG_INSULT_TARGET_NAME).performClick().performTextInput("Ädu")

        // Execute
        composeTestRule.onNodeWithTag(TEST_TAG_INSULT_BUTTON).performClick()

        composeTestRule.waitForIdle()

        // Verify
        composeTestRule.onNodeWithTag(TEST_TAG_INSULT_TEXT).assertTextContains("Ädu", substring = true)

        assertWithMessage("Media player did not play")
            .that(InsultSpeechPlayer.playCount).isEqualTo(playCountBefore + 1)
    }

    @Before
    fun registerIdlingResource() {
        fetchInsultIdlingResource = composeTestRule.activity.viewModel.getIdlingResource()
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
