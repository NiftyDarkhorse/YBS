package com.example.ybschallenge

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ybschallenge.component.FeedPhotos
import com.example.ybschallenge.component.TagTextField
import com.example.ybschallenge.repository.FlickrFeedRepositoryImpl
import com.example.ybschallenge.viewmodel.SearchFeedViewModel
import com.example.ybschallenge.destinations.FeedPhotoScreenDestination
import com.example.ybschallenge.destinations.FeedUserPhotosScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Screen that provides the ability to search by tags and/or author
 *
 * @param modifier
 * @param navigator
 * @param viewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SearchFeedScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: SearchFeedViewModel = SearchFeedViewModel(FlickrFeedRepositoryImpl()),
) {
    val searchTags = rememberMutableStateListOf<String>()
    var allTagsSearch by rememberSaveable { mutableStateOf(false) }
    var authorQuery by rememberSaveable { mutableStateOf("") }

    LocalContext.current.also { context ->
        val errorGettingFeed = stringResource(id = R.string.error_getting_feed_to_search)
        // Ensure any previous observers registered are removed so the observable fires only one.
        viewModel.errorGettingFeed.removeObservers(LocalLifecycleOwner.current)
        viewModel.errorGettingFeed.observe(LocalLifecycleOwner.current) {
            Toast.makeText(context, errorGettingFeed, Toast.LENGTH_SHORT).show()
        }
        val noMatchingItems = stringResource(id = R.string.search_no_results)
        // Ensure any previous observers registered are removed so the observable fires only one.
        viewModel.noResults.removeObservers(LocalLifecycleOwner.current)
        viewModel.noResults.observe(LocalLifecycleOwner.current) {
            if (it) {
                Toast.makeText(context, noMatchingItems, Toast.LENGTH_SHORT).show()
            }
        }
    }

    BackHandler {
        if (viewModel.searchResult.value?.items?.isNotEmpty() == true) {
            viewModel.clearSearchResults()
        } else {
            navigator.navigateUp()
        }
    }
    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopAppBar(
                    title = {
                        Text(
                            text = if (viewModel.searchResult.value?.items?.isNotEmpty() == true) {
                                viewModel.searchResult.value?.title ?: ""
                            } else {
                                stringResource(R.string.title_search_feed)
                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (viewModel.searchResult.value?.items?.isNotEmpty() == true) {
                                viewModel.clearSearchResults()
                            } else {
                                navigator.navigateUp()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            // Show the FAB only if we don't have any items to show and we are currently not loading new items
            if ((viewModel.searchResult.value == null || viewModel.searchResult.value?.items?.isEmpty() == true) && !viewModel.isLoadingFeed.value) {
                Button(
                    onClick = {
                        viewModel.searchFeed(
                            tags = searchTags,
                            matchAllTags = allTagsSearch,
                            author = authorQuery
                        )
                    },
                    modifier = Modifier
                        .defaultMinSize(minWidth = 56.dp, minHeight = 56.dp)
                        .testTag("SearchFAB"),
                    enabled = searchTags.isNotEmpty() || authorQuery.isNotEmpty(),
                    shape = CircleShape,

                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(id = R.string.search_fab), 
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        },
        modifier = modifier
    ) { contentPadding ->
        if (viewModel.searchResult.value?.items?.isNotEmpty() == true) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                viewModel.searchResult.value?.items?.let {
                    FeedPhotos(
                        feedItems = it,
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
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .fillMaxSize()
            ) {
                if (viewModel.isLoadingFeed.value) {
                    CircularProgressIndicator()
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(contentPadding)
                    ) {
                        Column {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                TagTextField(
                                    tags = searchTags,
                                    onTagAdd = { tag ->
                                        searchTags.add(tag)
                                    },
                                    onTagRemove = { tag ->
                                        searchTags.remove(tag)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            allTagsSearch = !allTagsSearch
                                        }
                                ) {
                                    Checkbox(
                                        checked = allTagsSearch,
                                        onCheckedChange = {}
                                    )
                                    Text(text = stringResource(R.string.search_match_all_tags))
                                }
                            }
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                TextField(
                                    value = authorQuery,
                                    onValueChange = { newValue ->
                                        authorQuery = newValue.trim()
                                    },
                                    maxLines = 1,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                    placeholder = {
                                        Text(text = stringResource(R.string.search_author_hint))
                                    },
                                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                                    modifier = Modifier
                                        .background(Color.Transparent)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}