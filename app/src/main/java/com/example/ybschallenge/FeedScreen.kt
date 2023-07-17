package com.example.ybschallenge

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ybschallenge.component.FeedPhotosWithSpinner
import com.example.ybschallenge.destinations.FeedPhotoScreenDestination
import com.example.ybschallenge.destinations.FeedUserPhotosScreenDestination
import com.example.ybschallenge.destinations.SearchFeedScreenDestination
import com.example.ybschallenge.repository.FlickrFeedRepositoryImpl
import com.example.ybschallenge.viewmodel.FeedViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Screen to show the feed and it's items
 *
 * @param modifier
 * @param viewModel
 * @param navigator
 */
@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = FeedViewModel(FlickrFeedRepositoryImpl()),
    navigator: DestinationsNavigator,
) {
    var gridView by rememberSaveable { mutableStateOf(false) }
    var showErrorInLayout by remember { mutableStateOf(false) }
    val feed by remember {
        viewModel.feed
    }

    LocalContext.current.also { context ->
        val noMatchingItems = stringResource(id = R.string.error_getting_feed)
        // Ensure any previous observers registered are removed so the observable fires only one.
        viewModel.errorGettingFeed.removeObservers(LocalLifecycleOwner.current)
        viewModel.errorGettingFeed.observe(LocalLifecycleOwner.current) {
            if (feed?.items.isNullOrEmpty() == false && it) {
                Toast.makeText(context, noMatchingItems, Toast.LENGTH_SHORT).show()
            } else {
                showErrorInLayout = it
            }
        }
    }

    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopAppBar(
                    title = {
                        Text(
                            text = feed?.title ?: stringResource(id = R.string.title_feed_fallback),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            gridView = !gridView
                        }) {
                            Icon(
                                painter = if (gridView) painterResource(
                                    id = R.drawable.list_view
                                )
                                else painterResource(
                                    id = R.drawable.grid_view
                                ),
                                contentDescription = if (gridView) stringResource(
                                    id = R.string.content_description_list_view
                                ) else
                                    stringResource(
                                        id = R.string.content_description_grid_view
                                    )
                            )
                        }
                        IconButton(onClick = {
                            viewModel.refreshFeed()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = stringResource(id = R.string.content_description_refresh)
                            )
                        }
                        IconButton(onClick = {
                            navigator.navigate(
                                SearchFeedScreenDestination()
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.content_description_search)
                            )
                        }
                    }
                )
            }
        },
        modifier = modifier
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            if (showErrorInLayout) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 56.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.error_getting_feed_in_layout),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp),
                    )
                }
            } else {
                Column(
                    modifier = modifier
                ) {
                    FeedPhotosWithSpinner(
                        showLoadingIndicator = viewModel.isLoadingFeed.value,
                        feedItems = feed?.items ?: emptyList(),
                        gridView = gridView,
                        photoClicked = { feedItem ->
                            navigator.navigate(
                                FeedPhotoScreenDestination(feedItem = feedItem)
                            )
                        },
                        userClicked = { feedItem ->
                            feedItem.authorId?.let { authorId ->
                                navigator.navigate(
                                    FeedUserPhotosScreenDestination(authorId = authorId)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
    if (feed == null) {
        viewModel.initialise()
    }
}