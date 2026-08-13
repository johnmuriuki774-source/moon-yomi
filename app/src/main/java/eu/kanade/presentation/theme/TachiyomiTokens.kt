package eu.kanade.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import eu.kanade.presentation.components.ui.AppShapes
import eu.kanade.presentation.components.ui.AppSpacing
import eu.kanade.presentation.components.ui.AppTypography

object TachiyomiTokens {
    val spacing = AppSpacing
    val shapes = AppShapes
    val typography = AppTypography
}

@Composable
fun TachiyomiTheme.tokens() = TachiyomiTokens
