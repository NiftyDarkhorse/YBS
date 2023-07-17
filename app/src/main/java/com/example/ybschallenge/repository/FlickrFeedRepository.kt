package com.example.ybschallenge.repository

import com.example.ybschallenge.dataobject.Feed

/**
 * Interface to enforce repository functions on a repository
 */
interface FlickrFeedRepository {
    /**
     * Get the feed
     *
     * @param userId optional userId to only retrieve feed data for a user
     * @return the feed from Flickr
     */
    suspend fun getFeed(userId: String? = null): Feed?

    /**
     * Search the feed
     *
     * @param tags to search for
     * @param matchAll whether we are matching all tags or not
     * @param author an author to search for
     * @return the feed from Flickr
     */
    suspend fun searchFeed(tags: List<String>, matchAll: Boolean, author: String): Feed?
}