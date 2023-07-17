package com.example.ybschallenge.repository

import com.example.ybschallenge.dataobject.FeedItem
import com.example.ybschallenge.network.FlickerNetwork
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Implementation of the Flickr Feed Repository interface
 */
open class FlickrFeedRepositoryImpl: FlickrFeedRepository {

    override suspend fun getFeed(userId: String?) = suspendCoroutine { continuation ->
        FlickerNetwork.getFeed(
            userId = userId
        ).apply {
            continuation.resume(this)
        }
    }

    override suspend fun searchFeed(tags: List<String>, matchAll: Boolean, author: String) = suspendCoroutine { continuation ->
        FlickerNetwork.searchFeed(
            tags = tags,
            matchAll = matchAll
        ).apply {
            if (author.isNotEmpty()) {
                // List to hold matching items in
                val matches = mutableListOf<FeedItem>()
                this?.items?.forEach {
                    // If the author for the current item matches the author being searched add it to the matching items list
                    if (it.authorDisplay()?.compareTo(author) == 0) {
                        matches.add(it)
                    }
                }
                // Set the items list on the feed object to the matching items list
                this?.items = matches
            }
            continuation.resume(this)
        }
    }
}