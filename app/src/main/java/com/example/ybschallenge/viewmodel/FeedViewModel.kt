package com.example.ybschallenge.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ybschallenge.dataobject.Feed
import com.example.ybschallenge.repository.FlickrFeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A view model to get and store the Flickr feed data
 *
 * @property repository a repository that implements the FlickrFeedRepository interface
 */
class FeedViewModel(private val repository: FlickrFeedRepository): ViewModel() {

    private val _isLoadingFeed = mutableStateOf(false)
    val isLoadingFeed: MutableState<Boolean>
        get() = _isLoadingFeed

    private val _feed = mutableStateOf<Feed?>(null)
    val feed: MutableState<Feed?>
        get() = _feed

    private val _errorGettingFeed = MutableLiveData<Boolean>()
    val errorGettingFeed: MutableLiveData<Boolean>
        get() = _errorGettingFeed

    /**
     * Initialise
     *
     * @param userId id of the user that we want to see the feed for (optional)
     */
    fun initialise(userId: String? = null) {
        refreshFeed(userId)
    }

    /**
     * Refresh feed
     *
     * @param userId id of the user that we want to see the feed for (optional)
     */
    fun refreshFeed(userId: String? = null) {
        _errorGettingFeed.value = false
        _isLoadingFeed.value = true
        // Ensure we are not running this on the UI thread by using the Coroutine
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _feed.value = repository.getFeed(userId)
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _errorGettingFeed.value = true
                }
            }
            _isLoadingFeed.value = false
        }
    }
}

