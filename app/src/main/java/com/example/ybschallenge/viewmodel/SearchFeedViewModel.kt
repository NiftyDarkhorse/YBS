package com.example.ybschallenge.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ybschallenge.dataobject.Feed
import com.example.ybschallenge.repository.FlickrFeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A view model to search and store the Flickr feed data
 *
 * @property repository a repository that implements the FlickrFeedRepository interface
 */
class SearchFeedViewModel(private val repository: FlickrFeedRepository): ViewModel() {

    private val _isLoadingFeed = mutableStateOf(false)
    val isLoadingFeed: MutableState<Boolean>
        get() = _isLoadingFeed

    private val _searchResults = mutableStateOf<Feed?>(null)
    val searchResult: MutableState<Feed?>
        get() = _searchResults

    private val _noResults = MutableLiveData<Boolean>()
    val noResults: MutableLiveData<Boolean>
        get() = _noResults

    private val _errorGettingFeed = MutableLiveData<Boolean>()
    val errorGettingFeed: MutableLiveData<Boolean>
        get() = _errorGettingFeed

    /**
     * Search feed
     *
     * @param tags to match
     * @param matchAllTags if we are to match all tags or just any
     * @param author the author we want to search for
     */
    fun searchFeed(tags: List<String>, matchAllTags: Boolean, author: String) {
        _isLoadingFeed.value = true
        // Ensure we are not running this on the UI thread by using the Coroutine
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchResults.value = repository.searchFeed(tags, matchAllTags, author)
                withContext(Dispatchers.Main) {
                    if (_searchResults.value?.items?.isEmpty() == true) {
                        _noResults.value = true
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _errorGettingFeed.value = true
                }
            }
            _isLoadingFeed.value = false
        }
    }

    /**
     * Clear the current search results
     *
     */
    fun clearSearchResults() {
        _searchResults.value = null
    }
}

