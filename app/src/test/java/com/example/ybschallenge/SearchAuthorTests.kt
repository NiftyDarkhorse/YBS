package com.example.ybschallenge

import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(value = Parameterized::class)
class SearchAuthorTests(private val author: String, private val expectedFeedItemsSize: Int, private val expectedFeedITemTitle: String): BaseTests() {

    @Test
    fun test() {

        val vm = createSearchFeedModel()
        vm.searchFeed(emptyList(), false, author)
        // As the feed is an async operation we need to wait for the mocking
        // of the async operation to occur
        Thread.sleep(100)
        // Check the values we expect against the feed object set in the VM
        if (expectedFeedItemsSize > 0) {
            assertEquals(expectedFeedITemTitle, vm.searchResult.value?.items?.first()?.title)
        } else {
            assertEquals(true, vm.searchResult.value?.items?.isEmpty())
        }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: searchAuthorTests({0})={1},{2}")
        fun data(): Iterable<Array<Any?>> {
            return listOf(
                arrayOf(AUTHOR_ID_1, 1, FEED_ITEM_TITLE_1),
                arrayOf(AUTHOR_ID_2, 2, FEED_ITEM_TITLE_2),
                arrayOf("", 0, ""),
            )
        }
    }
}