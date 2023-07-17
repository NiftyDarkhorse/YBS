package com.example.ybschallenge.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ybschallenge.R

/**
 * Shows tag chips in a vertically expanding UI element
 *
 * @param modifier
 * @param tags to show
 * @param onTagRemove a tag has been removed
 * @receiver
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagChips(
    modifier: Modifier = Modifier,
    tags: List<String>,
    onTagRemove: (String) -> Unit
) {
    Surface(modifier = Modifier.padding(8.dp)) {
        if (tags.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .testTag("TagTextFieldTagsContainer")
            ) {
                tags.forEach { tag ->
                    TagChip(
                        tag = tag,
                        onTagRemove = {
                            onTagRemove(tag)
                        }
                    )
                }
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .background(Color.Transparent)
                    .height(32.dp)
            ) {
                Text(
                    text = stringResource(R.string.search_tag_tags_appear_here)
                )
            }
        }
    }
}

/**
 * A chip to display the tag text
 *
 * @param modifier
 * @param tag to show
 * @param onTagRemove a tag has been removed
 * @receiver
 */
@Composable
fun TagChip(
    modifier: Modifier = Modifier,
    tag: String,
    onTagRemove: () -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primary,
    ) {
        Row(modifier = Modifier
            .padding(4.dp)
        ) {
            Text(
                text = tag,
                modifier = Modifier.testTag("TagChip")
            )
            IconButton(
                onClick = {
                    onTagRemove()
                },
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
                    .padding(start = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(id = R.string.content_description_remove_tag)
                )
            }
        }
    }
}