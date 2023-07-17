package com.example.ybschallenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ybschallenge.dataobject.FeedItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Screen to show an item from the feed
 *
 * @param modifier
 * @param navigator
 * @param feedItem to show
 */
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun FeedPhotoScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    feedItem: FeedItem
) {
    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopAppBar(
                    title = {
                        Text(
                            text = feedItem.title ?: "",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.testTag("ScreenTitle")
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigator.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.content_description_back)
                            )
                        }
                    }
                )
            }
        },
        modifier = modifier
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(feedItem.media?.url)
                            .crossfade(true)
                            .build(),
                        contentDescription = feedItem.description,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .wrapContentSize(
                                align = Alignment.Center
                            )
                            .height(300.dp)
                            .clip(
                                shape = RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 8.dp,
                                    bottomStart = 8.dp,
                                    bottomEnd = 8.dp
                                )
                            )
                            .align(Alignment.CenterHorizontally)
                            .testTag("PhotoImage")
                    )
                }
                Column {
                    val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                    RenderFeedItemInfoWithHeader(
                        modifier = modifier,
                        header = stringResource(R.string.header_photo_title),
                        item = feedItem.title ?: "",
                        testTag = "PhotoTitle"
                    )
                    RenderFeedItemInfoWithHeader(
                        modifier = modifier,
                        header = stringResource(R.string.header_photo_tags),
                        item = feedItem.tags ?: "",
                        testTag = "PhotoTags"
                    )
                    RenderFeedItemInfoWithHeader(
                        modifier = modifier,
                        header = stringResource(R.string.header_photo_author),
                        item = feedItem.authorDisplay() ?: "",
                        testTag = "PhotoAuthor"
                    )
                    RenderFeedItemInfoWithHeader(
                        modifier = modifier,
                        header = stringResource(R.string.header_photo_taken),
                        item = feedItem.dateTaken?.let { format.format(it) } ?: "",
                        testTag = "PhotoTaken"
                    )
                    RenderFeedItemInfoWithHeader(
                        modifier = modifier,
                        header = stringResource(R.string.header_photo_published),
                        item = feedItem.published?.let { format.format(it) } ?: "",
                        testTag = "PhotoPublished"
                    )
                    RenderFeedItemInfoWithHeader(
                        modifier = modifier,
                        header = stringResource(R.string.header_photo_description),
                        item =  HtmlCompat.fromHtml(
                            feedItem.description ?: "",
                            HtmlCompat.FROM_HTML_MODE_COMPACT
                        ).toString(),
                        testTag = "PhotoDescription"
                    )
                }
            }
        }
    }
}

/**
 * Render feed item info with header
 *
 * @param modifier
 * @param header to show
 * @param item to show
 */
@Composable
private fun RenderFeedItemInfoWithHeader(
    modifier: Modifier = Modifier,
    header: String,
    item: String,
    testTag: String,
) {
    if (item.trim().isEmpty()) return
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = header,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.testTag("${testTag}Label")
        )
        Text(
            text = item,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.testTag(testTag)
        )
    }
}