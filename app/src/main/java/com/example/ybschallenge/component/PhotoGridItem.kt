package com.example.ybschallenge.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ybschallenge.R
import com.example.ybschallenge.dataobject.FeedItem

/**
 * Layout for a feed item when in a grid
 *
 * @param modifier
 * @param feedItem to show
 * @param photoClicked a photo has been clicked
 * @param userClicked a user has been clicked
 * @receiver
 * @receiver
 */
@Composable
fun PhotoGridItem(
    modifier: Modifier = Modifier,
    feedItem: FeedItem,
    photoClicked: () -> Unit,
    userClicked: () -> Unit
) {
    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(feedItem.media?.url)
                .crossfade(true)
                .build(),
            contentDescription = feedItem.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
                .clickable { photoClicked() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.Black.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
                .clickable { userClicked() }
        ) {
            Column(
                modifier = Modifier.padding(6.dp)
            ) {
                feedItem.authorDisplay()?.let {
                    TextWithIconAtStart(
                        text = it,
                        maxLines = 1,
                        icon = painterResource(id = R.drawable.ic_person),
                        textIconColor = Color.White,
                        textClicked = userClicked
                    )
                }
                feedItem.tags?.let {
                    if (it.trim().isNotEmpty()) {
                        TextWithIconAtStart(
                            text = it,
                            maxLines = 1,
                            icon = painterResource(id = R.drawable.ic_tag),
                            textIconColor = Color.White,
                            textClicked = {}
                        )
                    }
                }
            }
        }
    }
}