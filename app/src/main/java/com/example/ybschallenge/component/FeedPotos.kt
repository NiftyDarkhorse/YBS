package com.example.ybschallenge.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ybschallenge.dataobject.FeedItem

/**
 * Show the feed photos
 *
 * @param modifier
 * @param feedItems list of feed items to show
 * @param showGridView optional flag to show a grid view
 * @param photoClicked a photo has been clicked
 * @param userClicked a user has been clicked
 * @receiver
 * @receiver
 */
@Composable
fun FeedPhotos(
    modifier: Modifier = Modifier,
    feedItems: List<FeedItem>,
    showGridView: Boolean = false,
    photoClicked: (item: FeedItem) -> Unit,
    userClicked: (item: FeedItem) -> Unit,
) {
    if (showGridView) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(2.dp),
            modifier = Modifier.testTag("FeedPhotosGrid")
        ) {
            items(feedItems) { item ->
                PhotoGridItem(
                    feedItem = item,
                    photoClicked = {
                        photoClicked(item)
                    },
                    userClicked = {
                        userClicked(item)
                    }
                )
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier.testTag("FeedPhotosList")
        ) {
            items(feedItems) { item ->
                PhotoListItem(
                    feedItem = item,
                    photoClicked = {
                        photoClicked(item)
                    },
                    userClicked = {
                        userClicked(item)
                    }
                )
            }
        }
    }
}