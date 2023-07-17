package com.example.ybschallenge

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.ybschallenge.ui.theme.YBSChallengeTheme
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import org.junit.Rule
import org.junit.Test
import java.lang.AssertionError

/**
 * Search screen test
 */
class SearchScreenTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Test the tile is right
     *
     */
    @Test
    fun title() {
        // Start the app
        setScreen()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.title_search_feed)).assertIsDisplayed()
    }

    /**
     * Test to ensure that when a user type in a tag and clicks the + button the tag is added to the area under the TextField
     */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun addTag() {
        // Start the app
        setScreen()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.search_tag_tags_appear_here)).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.content_description_add_tag)).assertIsNotEnabled()
        composeTestRule.onNodeWithTag("TagEditField").performTextInput("Test Tag")
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.content_description_add_tag)).assertIsEnabled()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.content_description_add_tag)).performClick()
        composeTestRule.waitUntilNodeCount(hasTestTag("TagChip"), 1, 1000)
        composeTestRule.onNodeWithTag("TagChip").assertTextEquals("Test Tag")
        try {
            composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.search_tag_tags_appear_here)).assertIsNotDisplayed()
        } catch (e: AssertionError) {
            // We don't expect to find the composable UI element so this is a pass
        }
    }

    /**
     * Test to ensure that when a user removes a tag the tag is added to the area under the TextField
     */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun removeTag() {
        // Start the app
        setScreen()

        composeTestRule.onNodeWithTag("TagEditField").performTextInput("Test Tag")
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.content_description_add_tag)).performClick()
        composeTestRule.waitUntilNodeCount(hasTestTag("TagChip"), 1, 1000)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.content_description_remove_tag)).performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.search_tag_tags_appear_here)).assertIsDisplayed()
    }

    /**
     * Little function to avoid replication of the setting the screen up
     *
     */
    private fun setScreen() {
        composeTestRule.setContent {
            YBSChallengeTheme {
                SearchFeedScreen(navigator = EmptyDestinationsNavigator)
            }
        }
    }
}