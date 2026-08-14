package eu.kanade.tachiyomi.ui.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import eu.kanade.domain.entries.anime.model.toDomainAnime
import eu.kanade.tachiyomi.source.anime.AndroidAnimeSourceManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tachiyomi.domain.category.anime.interactor.GetVisibleAnimeCategories
import tachiyomi.domain.category.model.Category
import tachiyomi.domain.entries.anime.interactor.GetLibraryAnime
import tachiyomi.domain.entries.anime.interactor.NetworkToLocalAnime
import tachiyomi.domain.entries.anime.model.Anime
import tachiyomi.domain.history.anime.interactor.GetAnimeHistory
import tachiyomi.domain.history.anime.model.AnimeHistoryWithRelations
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class HomeTabScreenModel(
    private val getLibraryAnime: GetLibraryAnime = Injekt.get(),
    private val getAnimeHistory: GetAnimeHistory = Injekt.get(),
    private val getVisibleAnimeCategories: GetVisibleAnimeCategories = Injekt.get(),
    private val sourceManager: AndroidAnimeSourceManager = Injekt.get(),
    private val networkToLocalAnime: NetworkToLocalAnime = Injekt.get(),
) : StateScreenModel<HomeTabScreenModel.State>(State()) {

    init {
        screenModelScope.launch {
            combine(
                getAnimeHistory.subscribe(query = ""),
                getLibraryAnime.subscribe(),
                getVisibleAnimeCategories.subscribe(),
            ) { history, library, categories ->
                Triple(history, library, categories)
            }.collectLatest { (history, library, categories) ->
                mutableState.update {
                    it.copy(
                        isLoading = false,
                        continueWatching = history,
                        libraryAnime = library,
                        categories = categories,
                    )
                }
            }
        }

        screenModelScope.launch {
            val sources = sourceManager.getOnlineSources()
            if (sources.isNotEmpty()) {
                val source = sources.first()
                try {
                    val popularSAnime = source.getPopularAnime(1).animes
                    val popularAnime = popularSAnime.map { networkToLocalAnime.await(it.toDomainAnime(source.id)) }
                    val latestSAnime = source.getLatestUpdates(1).animes
                    val latestAnime = latestSAnime.map { networkToLocalAnime.await(it.toDomainAnime(source.id)) }

                    mutableState.update {
                        it.copy(
                            popularAnime = popularAnime,
                            latestAnime = latestAnime,
                        )
                    }
                } catch (e: Exception) {
                    // Handle source failure gracefully
                }
            }
        }
    }

    data class State(
        val isLoading: Boolean = true,
        val continueWatching: List<AnimeHistoryWithRelations> = emptyList(),
        val libraryAnime: List<tachiyomi.domain.library.anime.LibraryAnime> = emptyList(),
        val categories: List<Category> = emptyList(),
        val popularAnime: List<Anime> = emptyList(),
        val latestAnime: List<Anime> = emptyList(),
    )
}
