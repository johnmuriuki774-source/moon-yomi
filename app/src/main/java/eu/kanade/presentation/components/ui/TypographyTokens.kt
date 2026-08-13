package eu.kanade.presentation.components.ui

import androidx.compose.material3.Typography
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTypography {
    val titleLarge = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
    )

    val titleMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
    )

    val body = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
    )

    val caption = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
    )
}

@Composable
fun typography() = Typography(
    displayLarge = AppTypography.titleLarge,
    titleLarge = AppTypography.titleLarge,
    titleMedium = AppTypography.titleMedium,
    bodyLarge = AppTypography.body,
    bodyMedium = AppTypography.body,
    labelSmall = AppTypography.caption,
)
