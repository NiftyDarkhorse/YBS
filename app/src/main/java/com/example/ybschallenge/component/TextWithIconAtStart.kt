package com.example.ybschallenge.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ybschallenge.R

/**
 * Text with icon at start UI component
 *
 * @param modifier
 * @param icon to show
 * @param text to show
 * @param maxLines to limit the text too (optional)
 * @param textIconColor color of the icon nd text (optional)
 * @param textClicked the text has been clicked
 * @receiver
 */
@Composable
fun TextWithIconAtStart(
    modifier: Modifier = Modifier,
    icon: Painter,
    text: String,
    maxLines: Int = 90,
    textIconColor: Color = MaterialTheme.colorScheme.onSurface,
    textClicked: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            painter = icon,
            contentDescription = stringResource(id = R.string.content_description_refresh),
            tint = textIconColor,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 2.dp)
        )
        Text(
            text = text,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            color = textIconColor,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier
                .weight(1f)
                .clickable { textClicked() }
        )
    }
}