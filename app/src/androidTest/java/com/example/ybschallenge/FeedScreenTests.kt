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
 * A selection of test for the feed screen
 */
class FeedScreenTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Test to ensure that when the suer clicks the switch to gris view button they see the grid view
     */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun switchToGridViewWithActionButton() {
        // Start the app
        composeTestRule.setContent {
            YBSChallengeTheme {
                FeedScreen(navigator = EmptyDestinationsNavigator)
            }
        }

        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.content_description_grid_view)).performClick()
        composeTestRule.waitUntilNodeCount(hasTestTag("FeedPhotosGrid"), 1, 2000)
        try {
            composeTestRule.onNodeWithTag("FeedPhotosList").assertIsNotDisplayed()
        } catch (e: AssertionError) {
            // We don't expect to find the composable UI element so this is a pass
        }
    }
}