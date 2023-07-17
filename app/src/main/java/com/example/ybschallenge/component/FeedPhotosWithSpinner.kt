package com.example.ybschallenge.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.ybschallenge.dataobject.FeedItem

/**
 * Show the feed photos with an options to show a progress bar instead
 *
 * @param modifier
 * @param showLoadingIndicator show a loading indicator if required
 * @param feedItems to show
 * @param gridView optional flag to show a grid view
 * @param photoClicked a photo has been clicked
 * @param userClicked a user has been clicked
 * @receiver
 * @receiver
 */
@Composable
fun FeedPhotosWithSpinner(
    modifier: Modifier = Modifier,
    showLoadingIndicator: Boolean = false,
    feedItems: List<FeedItem>,
    gridView: Boolean = false,
    photoClicked: (item: FeedItem) -> Unit,
    userClicked: (item: FeedItem) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (showLoadingIndicator) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.testTag("FeedLoadingIndicator")
                )
            }
        } else {
            FeedPhotos(
                feedItems = feedItems,
                showGridView = gridView,
                photoClicked = { feedItem ->
                    photoClicked(feedItem)
                },
                userClicked = { feedItem ->
                    userClicked(feedItem)
                }
            )
        }
    }
}