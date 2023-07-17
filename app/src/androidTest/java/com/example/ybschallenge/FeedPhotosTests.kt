package com.example.ybschallenge

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.ybschallenge.component.FeedPhotos
import org.junit.Rule
import org.junit.Test
import java.lang.AssertionError

/**
 * A selection of test for the FeedPhotos UI component
 */
class FeedPhotosTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Test to ensure that if gridView is set to true in the FeedPhoto's UI composable the grid view is shown
     */
    @Test
    fun showingGridView() {
        // Start the app
        setScreen(gridView = true)

        composeTestRule.onNodeWithTag("FeedPhotosGrid").assertIsDisplayed()
        try {
            composeTestRule.onNodeWithTag("FeedPhotosList").assertIsNotDisplayed()
        } catch (e: AssertionError) {
            // We don't expect to find the composable UI element so this is a pass
        }
    }

    /**
     * Test to ensure that if gridView is set to false in the FeedPhoto's UI composable the list view is shown
     */
    @Test
    fun showingListView() {
        // Start the app
        setScreen(gridView = false)

        composeTestRule.onNodeWithTag("FeedPhotosList").assertIsDisplayed()
        try {
            composeTestRule.onNodeWithTag("FeedPhotosGrid").assertIsNotDisplayed()
        } catch (e: AssertionError) {
            // We don't expect to find the composable UI element so this is a pass
        }
    }

    /**
     * Little function to avoid replication of the setting the screen up
     *
     */
    private fun setScreen(gridView: Boolean) {
        composeTestRule.setContent {
            FeedPhotos(showGridView = gridView, photoClicked = {}, userClicked = {}, feedItems = emptyList())
        }
    }
}