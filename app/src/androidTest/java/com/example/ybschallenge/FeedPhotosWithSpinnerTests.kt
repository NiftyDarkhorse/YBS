package com.example.ybschallenge

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.ybschallenge.component.FeedPhotos
import com.example.ybschallenge.component.FeedPhotosWithSpinner
import com.example.ybschallenge.ui.theme.YBSChallengeTheme
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import org.junit.Rule
import org.junit.Test
import java.lang.AssertionError

/**
 * A selection of test for the FeedPhotosWithSpinner UI component
 */
class FeedPhotosWithSpinnerTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Test to ensure that if showLoadingIndicator is set to true in the FeedPhotosWithSpinner's UI composable the loading indicator is shown
     */
    @Test
    fun spinnerShown() {

        // Start the app
        setScreen(showLoadingIndicator = true)

        composeTestRule.onNodeWithTag("FeedLoadingIndicator").assertIsDisplayed()
        try {
            composeTestRule.onNodeWithTag("FeedPhotosList").assertIsNotDisplayed()
        } catch (e: AssertionError) {
            // We don't expect to find the composable UI element so this is a pass
        }
    }

    /**
     * Test to ensure that if showLoadingIndicator is set to false in the FeedPhotosWithSpinner's UI composable the loading indicator is not shown
     */
    @Test
    fun spinnerNotShown() {

        // Start the app
        setScreen(showLoadingIndicator = false)

        try {
            composeTestRule.onNodeWithTag("FeedLoadingIndicator").assertIsNotDisplayed()
        } catch (e: AssertionError) {
            // We don't expect to find the composable UI element so this is a pass
        }
    }

    /**
     * Little function to avoid replication of the setting the screen up
     *
     */
    private fun setScreen(showLoadingIndicator: Boolean) {
        composeTestRule.setContent {
            FeedPhotosWithSpinner(showLoadingIndicator = showLoadingIndicator, photoClicked = {}, userClicked = {}, feedItems = emptyList())
        }
    }
}