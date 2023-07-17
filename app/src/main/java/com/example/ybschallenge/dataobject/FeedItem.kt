package com.example.ybschallenge.dataobject

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.Date

/**
 * Feed item object to store the feed data in from Flickr
 *
 * @property title
 * @property link
 * @property media
 * @property dateTaken
 * @property description
 * @property published
 * @property author
 * @property authorId
 * @property tags
 * @constructor Create empty Feed item
 */
@Parcelize
data class FeedItem(
    var title: String? = null,
    var link: String? = null,
    var media: Media? = null,
    @SerializedName("date_taken")
    var dateTaken: Date? = null,
    var description: String? = null,
    var published: Date? = null,
    var author: String? = null,
    @SerializedName("author_id")
    var authorId: String? = null,
    var tags: String? = null
) : Parcelable {
    fun authorDisplay() = if (author.isNullOrEmpty()) null else author?.replace("nobody@flickr.com (\"", "")?.replace("\"", "")
}

/**
 * Media object to store the feed data in from Flickr
 *
 * @property url
 * @constructor Create empty Media
 */
@Parcelize
data class Media (
    @SerializedName("m")
    var url: String? = null
) : Parcelable