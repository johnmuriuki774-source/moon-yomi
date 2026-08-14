package eu.kanade.tachiyomi.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.TabOptions
import eu.kanade.presentation.library.components.EntryComfortableGridItem
import eu.kanade.presentation.util.Tab
import eu.kanade.tachiyomi.ui.entries.anime.AnimeScreen
import tachiyomi.domain.entries.anime.model.asAnimeCover
import tachiyomi.presentation.core.screens.LoadingScreen

data object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                index = 0u,
                title = "Home",
                icon = rememberVectorPainter(Icons.Default.Home),
            )
        }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel { HomeTabScreenModel() }
        val state by screenModel.state.collectAsState()

        if (state.isLoading) {
            LoadingScreen()
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (state.libraryAnime.isNotEmpty()) {
                    item {
                        Text(
                            text = "Featured",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(state.libraryAnime.take(10)) { libraryAnime ->
                                EntryComfortableGridItem(
                                    title = libraryAnime.anime.title,
                                    onClick = { navigator.push(AnimeScreen(libraryAnime.anime.id)) },
                                    coverData = libraryAnime.anime.asAnimeCover(),
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Explore",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp),
                    )
                }
                item {
                    FlowRow(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        state.categories.forEach { category ->
                            FilterChip(
                                selected = false,
                                onClick = { /* TODO: Navigate to category */ },
                                label = { Text(text = category.name) },
                            )
                        }
                    }
                }

                if (state.continueWatching.isNotEmpty()) {
                    item {
                        Text(
                            text = "Continue Watching",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(state.continueWatching) { history ->
                                EntryComfortableGridItem(
                                    title = history.title,
                                    onClick = { navigator.push(AnimeScreen(history.animeId)) },
                                    coverData = history.coverData,
                                )
                            }
                        }
                    }
                }

                if (state.popularAnime.isNotEmpty()) {
                    item {
                        Text(
                            text = "Popular This Season",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(state.popularAnime) { anime ->
                                EntryComfortableGridItem(
                                    title = anime.title,
                                    onClick = { navigator.push(AnimeScreen(anime.id)) },
                                    onLongClick = { /* TODO */ },
                                    coverData = anime.asAnimeCover(),
                                )
                            }
                        }
                    }
                }

                if (state.latestAnime.isNotEmpty()) {
                    item {
                        Text(
                            text = "New Episodes",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(state.latestAnime) { anime ->
                                EntryComfortableGridItem(
                                    title = anime.title,
                                    onClick = { navigator.push(AnimeScreen(anime.id)) },
                                    onLongClick = { /* TODO */ },
                                    coverData = anime.asAnimeCover(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
