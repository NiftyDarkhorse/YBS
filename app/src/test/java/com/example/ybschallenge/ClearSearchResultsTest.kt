package com.example.ybschallenge

import org.junit.Test
import org.junit.Assert.*

class ClearSearchResultsTest: BaseTests() {

    @Test
    fun test() {
        val vm = createSearchFeedModel()
        vm.searchFeed(emptyList(), false, "")
        // As the search feed is an async operation we need to wait for the mocking
        // of the async operation to occur
        Thread.sleep(100)
        // Check we have search results variable set
        assertNotNull(vm.searchResult.value)
        vm.clearSearchResults()
        // Check it has been cleared
        assertNull(vm.searchResult.value)
    }
}