package com.example.ybschallenge

import com.example.ybschallenge.dataobject.Feed
import com.example.ybschallenge.dataobject.FeedItem
import com.example.ybschallenge.dataobject.Media
import com.example.ybschallenge.repository.FlickrFeedRepository
import com.example.ybschallenge.viewmodel.FeedViewModel
import com.example.ybschallenge.viewmodel.SearchFeedViewModel
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import java.util.Date

open class BaseTests {

    fun createFeedModel(): FeedViewModel {
        return FeedViewModel(createMockRepository()).apply {
            initialise()
        }
    }

    fun createSearchFeedModel(): SearchFeedViewModel {
        return SearchFeedViewModel(createMockRepository())
    }

    private fun createMockRepository(): FlickrFeedRepository{
        // Create a mock flickrFeedRepository
        val repository = Mockito.mock(FlickrFeedRepository::class.java)
        // Mock out the getFeed() function
        repository.stub {
            onBlocking {
                getFeed()
            }.doReturn(
                feed
            )
        }
        repository.stub {
            onBlocking {
                searchFeed(any(), any(), any())
            }.doReturn(
                feed
            )
        }
        return repository
    }

    private val feed = Feed(
        title = FEED_TITLE,
        items = listOf(
            FeedItem(title = FEED_ITEM_TITLE_1, link = LINK_1, media = Media(url = MEDIA_1), dateTaken = Date(), description = DESCRIPTION_1, published = Date(), author = AUTHOR_1, authorId = AUTHOR_ID_1, tags = TAGS_1),
            FeedItem(title = FEED_ITEM_TITLE_2, link = LINK_1, media = Media(url = MEDIA_1), dateTaken = Date(), description = DESCRIPTION_1, published = Date(), author = AUTHOR_2, authorId = AUTHOR_ID_2, tags = TAGS_1)
        )
    )

    companion object {
        const val FEED_TITLE = "Feed Title"
        const val FEED_ITEM_TITLE_1 = "Feed Item Title 1"
        const val FEED_ITEM_TITLE_2 = "Feed Item Title 2"
        const val LINK_1 = "Link 1"
        const val MEDIA_1 = "Media 1"
        const val DESCRIPTION_1 = "Description 1"
        const val AUTHOR_1 = "Author 1"
        const val AUTHOR_2 = "Author 2"
        const val AUTHOR_ID_1 = "Author ID 1"
        const val AUTHOR_ID_2 = "Author ID 2"
        const val TAGS_1 = "Tags 1"
    }
}