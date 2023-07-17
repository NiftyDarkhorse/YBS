package com.example.ybschallenge.dataobject

/**
 * Feed object to store the feed data in from Flickr
 *
 * @property title of the feed
 * @property items items in the feed
 * @constructor Create empty Feed
 */
data class Feed(
    var title: String? = null,
    var items: List<FeedItem>? = null
)