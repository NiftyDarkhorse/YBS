package com.example.ybschallenge.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.ybschallenge.R

/**
 * Show a tex input field the enables the user to add and remove tags
 *
 * @param tags to show
 * @param modifier
 * @param onTagAdd a tag has been added
 * @param onTagRemove a tag has ben removed
 * @receiver
 * @receiver
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagTextField(
    tags: List<String>,
    modifier: Modifier = Modifier,
    onTagAdd: (String) -> Unit,
    onTagRemove: (String) -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.outline.copy(
            alpha = 0.1f
        ))
    ) {
        TextField(
            value = query,
            onValueChange = { newValue ->
                query = newValue.trim()
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = {
                Text(text = stringResource(R.string.search_tag_hint))
            },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            trailingIcon = {
                IconButton(
                    onClick = {
                        onTagAdd(query)
                        query = ""
                    },
                    enabled = query.isNotEmpty(),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.content_description_add_tag)
                    )
                }
            },
            modifier = Modifier
                .background(Color.Transparent)
                .weight(1f)
                .testTag("TagEditField")
        )
    }
    TagChips(
        tags = tags,
        onTagRemove = { tag ->
            onTagRemove(tag)
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
}