package com.example.ybschallenge

import org.junit.Test
import org.junit.Assert.*

class FeedViewModelVariableSetTest: BaseTests() {

    @Test
    fun test() {
        val vm = createFeedModel()
        // As the feed is an async operation we need to wait for the mocking
        // of the async operation to occur
        Thread.sleep(100)
        // Check the values we expect against the feed object set in the VM
        assertEquals(FEED_TITLE, vm.feed.value?.title)
        assertEquals(2, vm.feed.value?.items?.size)
        assertEquals(FEED_ITEM_TITLE_1, vm.feed.value?.items?.first()?.title)
        assertEquals(LINK_1, vm.feed.value?.items?.first()?.link)
        assertEquals(MEDIA_1, vm.feed.value?.items?.first()?.media?.url)
        assertEquals(DESCRIPTION_1, vm.feed.value?.items?.first()?.description)
        assertEquals(AUTHOR_1, vm.feed.value?.items?.first()?.author)
        assertEquals(AUTHOR_ID_1, vm.feed.value?.items?.first()?.authorId)
        assertEquals(TAGS_1, vm.feed.value?.items?.first()?.tags)
    }
}