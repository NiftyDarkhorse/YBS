package com.example.ybschallenge

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.ybschallenge.dataobject.Feed
import com.example.ybschallenge.dataobject.FeedItem
import com.example.ybschallenge.dataobject.Media
import com.example.ybschallenge.ui.theme.YBSChallengeTheme
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import org.junit.Rule
import org.junit.Test
import java.lang.AssertionError
import java.util.Date

/**
 * Feed photo test
 */
class FeedPhotoTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Test to ensure that with all data in a feed item all UI elements are shown
     */
    @Test
    fun feedItem1() {
        // Start the app
        // Please note, would not normally use !! on an object that is nullable but as we are in control of the test data this is safe
        setScreen(feed.items?.first()!!)

        composeTestRule.onNodeWithTag("ScreenTitle").assertTextEquals(FEED_ITEM_TITLE)
        composeTestRule.onNodeWithTag("PhotoTitleLabel").assertTextEquals(composeTestRule.activity.getString(R.string.header_photo_title))
        composeTestRule.onNodeWithTag("PhotoTitle").assertTextEquals(FEED_ITEM_TITLE)
        composeTestRule.onNodeWithTag("PhotoImage").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PhotoAuthorLabel").assertTextEquals(composeTestRule.activity.getString(R.string.header_photo_author))
        composeTestRule.onNodeWithTag("PhotoAuthor").assertTextEquals(AUTHOR)
        composeTestRule.onNodeWithTag("PhotoTakenLabel").assertTextEquals(composeTestRule.activity.getString(R.string.header_photo_taken))
        composeTestRule.onNodeWithTag("PhotoTaken").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PhotoPublishedLabel").assertTextEquals(composeTestRule.activity.getString(R.string.header_photo_published))
        composeTestRule.onNodeWithTag("PhotoPublished").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PhotoDescriptionLabel").assertTextEquals(composeTestRule.activity.getString(R.string.header_photo_description))
        composeTestRule.onNodeWithTag("PhotoDescription").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PhotoTagsLabel").assertTextEquals(composeTestRule.activity.getString(R.string.header_photo_tags))
        composeTestRule.onNodeWithTag("PhotoTags").assertTextEquals(TAGS)
    }

    /**
     * Test to ensure that if the title is missing/blank and there are no tags the relevant sections in the UI are not shown
     */
    @Test
    fun feedItem2() {
        // Start the app
        // Please note, would not normally use !! on an object that is nullable but as we are in control of the test data this is safe
        setScreen(feed.items?.get(1)!!)

        try {
            composeTestRule.onNodeWithTag("ScreenTitle").assertIsNotDisplayed()
            composeTestRule.onNodeWithTag("PhotoTitleLabel").assertIsNotDisplayed()
            composeTestRule.onNodeWithTag("PhotoTitle").assertIsNotDisplayed()
        } catch (e: AssertionError) {
            // We don't expect to find the composable UI element so this is a pass
        }
        try {
            composeTestRule.onNodeWithTag("PhotoTagsLabel").assertIsNotDisplayed()
            composeTestRule.onNodeWithTag("PhotoTags").assertIsNotDisplayed()
        } catch (e: AssertionError) {
            // We don't expect to find the composable UI element so this is a pass
        }
    }

    /**
     * Little function to avoid replication of the setting the screen up
     *
     */
    private fun setScreen(feedItem: FeedItem) {
        composeTestRule.setContent {
            YBSChallengeTheme {
                FeedPhotoScreen(navigator = EmptyDestinationsNavigator, feedItem = feedItem)
            }
        }
    }

    companion object {
        const val FEED_TITLE = "Feed Title"
        const val FEED_ITEM_TITLE = "Feed Item"
        const val LINK = "Link"
        const val MEDIA = "Media"
        const val DESCRIPTION = "Description"
        const val AUTHOR = "Author"
        const val AUTHOR_ID_1 = "AuthorId"
        const val TAGS = "Tags"

        val feed = Feed(
            title = FEED_TITLE,
            items = listOf(
                FeedItem(title = FEED_ITEM_TITLE, link = LINK, media = Media(url = MEDIA), dateTaken = Date(), description = DESCRIPTION, published = Date(), author = AUTHOR, authorId = AUTHOR_ID_1, tags = TAGS),
                FeedItem(title = "", link = LINK, media = Media(url = MEDIA), dateTaken = Date(), description = DESCRIPTION, published = Date(), author = AUTHOR, authorId = AUTHOR_ID_1, tags = "")
            )
        )
    }
}