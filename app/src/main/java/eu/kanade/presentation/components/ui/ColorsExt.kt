package eu.kanade.presentation.components.ui

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object ColorsExt {
    @Composable
    fun surfaceVariant(): Color = MaterialTheme.colorScheme.surfaceVariant

    @Composable
    fun artworkOverlay(): Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

    @Composable
    fun progressColor(): Color = MaterialTheme.colorScheme.primary

    @Composable
    fun onProgressColor(): Color = MaterialTheme.colorScheme.onPrimary
}
