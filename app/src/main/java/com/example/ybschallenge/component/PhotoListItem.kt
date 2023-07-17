package com.example.ybschallenge.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ybschallenge.dataobject.FeedItem
import com.example.ybschallenge.R

/**
 * Layout for a feed item when in a list
 *
 * @param modifier
 * @param feedItem to show
 * @param photoClicked a photo has been clicked
 * @param userClicked a user has been clicked
 * @receiver
 * @receiver
 */
@Composable
fun PhotoListItem(
    modifier: Modifier = Modifier,
    feedItem: FeedItem,
    photoClicked: () -> Unit,
    userClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(feedItem.media?.url)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .build(),
                contentDescription = feedItem.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
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
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
            ) {
                feedItem.authorDisplay()?.let {
                    TextWithIconAtStart(
                        text = it,
                        maxLines = 1,
                        icon = painterResource(id = R.drawable.ic_person),
                        textClicked = userClicked
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                feedItem.tags?.let {
                    if (it.trim().isNotEmpty()) {
                        TextWithIconAtStart(
                            text = it,
                            maxLines = 2,
                            icon = painterResource(id = R.drawable.ic_tag),
                            textClicked = userClicked
                        )
                    }
                }
            }
        }
    }
}