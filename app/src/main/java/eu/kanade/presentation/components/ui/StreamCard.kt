package eu.kanade.presentation.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun StreamCard(
    title: String,
    imageUrl: String?,
    progress: Float? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .height(180.dp)
            .clip(AppShapes.medium)
            .clickable { onClick() },
        shape = AppShapes.medium,
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
            )
            // Gradient overlay for title readability
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                ColorsExt.artworkOverlay(),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.0f),
                            ),
                        ),
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(AppSpacing.medium)
            ) {
                Text(
                    text = title,
                    style = AppTypography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                if (progress != null) {
                    LinearProgressIndicator(
                        progress = progress.coerceIn(0f, 1f),
                        color = ColorsExt.progressColor(),
                        modifier = Modifier
                            .padding(top = AppSpacing.small)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}
