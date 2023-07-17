package com.example.ybschallenge.network

import com.example.ybschallenge.dataobject.Feed
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

private const val FEED_URL = "https://www.flickr.com/services/feeds/photos_public.gne?format=json"

/**
 * Helper object to execute the network request to Flickr
 */
object FlickerNetwork {

    /**
     * Get the Flicker feed
     *
     * @param userId optional userId to only retrieve feed data for a user
     * @return the feed data from Flickr
     */
    fun getFeed(
        userId: String? = null
    ): Feed? {
        return makeRequest(args = if(userId != null) "&id=$userId" else "")
    }

    /**
     * Search feed based on tags
     *
     * @param tags to search for
     * @param matchAll whether we are matching all tags or not
     * @return
     */
    fun searchFeed(
        tags: List<String>,
        matchAll: Boolean
    ): Feed? {
        return makeRequest(args = "&tags=${tags.joinToString(",")}&tagmode=${if(matchAll) "all" else "any"}")
    }

    /**
     * Make a request to Flickr
     *
     * @param args to put into the request
     * @return
     */
    private fun makeRequest(args: String): Feed? {
        val url = URL("$FEED_URL$args")
        (url.openConnection() as? HttpURLConnection)?.run {
            val br = BufferedReader(
                InputStreamReader(
                    inputStream,
                    StandardCharsets.UTF_8
                )
            )
            val sb = StringBuilder()
            var line: String?
            while (br.readLine().also { line = it } != null) {
                sb.append(line).append("\n")
            }
            br.close()
            //removing  "jsonFlickrFeed(" and remaining ")"
            val finalJson: String = sb.toString()
                .trim()
                .replace("jsonFlickrFeed(", "")
                .replace(")", "")
            return Gson().fromJson(finalJson, Feed::class.java)
        }
        return null
    }
}