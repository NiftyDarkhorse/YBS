package com.example.ybschallenge

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ybschallenge.component.FeedPhotosWithSpinner
import com.example.ybschallenge.viewmodel.FeedViewModel
import com.example.ybschallenge.repository.FlickrFeedRepositoryImpl
import com.example.ybschallenge.destinations.FeedPhotoScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Screen to show photo's for a specific user
 *
 * @param modifier
 * @param navigator
 * @param viewModel
 * @param authorId
 */
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun FeedUserPhotosScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: FeedViewModel = FeedViewModel(FlickrFeedRepositoryImpl()),
    authorId: String
) {
    val feed by remember {
        viewModel.feed
    }
    var gridView by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopAppBar(
                    title = {
                        Text(
                            text = feed?.title ?: "",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
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
                    },
                    actions = {
                        IconButton(onClick = {
                            gridView = !gridView
                        }) {
                            Icon(
                                painter = if (gridView) painterResource(id = R.drawable.list_view) else painterResource(
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
            FeedPhotosWithSpinner(
                showLoadingIndicator = feed == null,
                feedItems = feed?.items ?: emptyList(),
                gridView = gridView,
                photoClicked = { feedItem ->
                    navigator.navigate(
                        FeedPhotoScreenDestination(feedItem = feedItem)
                    )
                },
                userClicked = {}
            )
        }
    }
    viewModel.initialise(authorId)
}