package eu.kanade.tachiyomi.ui.browse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import eu.kanade.presentation.components.ui.ArtworkBanner
import eu.kanade.presentation.components.ui.StreamCard
import eu.kanade.presentation.theme.TachiyomiTheme
import eu.kanade.presentation.util.Screen

@Composable
fun BrowseTopArea(
    items: List<Pair<String, String?>>,
    modifier: Modifier = Modifier,
) {
    val navigator = LocalNavigator.currentOrThrow
    Column(modifier = modifier.fillMaxWidth().padding(12.dp)) {
        if (items.isNotEmpty()) {
            ArtworkBanner(title = items[0].first, imageUrl = items[0].second, onClick = {})
            LazyRow(modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
                items(items.drop(1).take(8)) { item ->
                    StreamCard(title = item.first, imageUrl = item.second, modifier = Modifier.padding(end = 8.dp))
                }
            }
        }
    }
}
