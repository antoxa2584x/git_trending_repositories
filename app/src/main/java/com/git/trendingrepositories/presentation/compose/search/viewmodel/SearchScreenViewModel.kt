package com.git.trendingrepositories.presentation.compose.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.git.trendingrepositories.data.mappers.toRepository
import com.git.trendingrepositories.domain.model.enums.SortPeriod
import com.git.trendingrepositories.domain.model.search.Repository
import com.git.trendingrepositories.domain.usecase.search.GetRepositoriesUseCase
import com.git.trendingrepositories.presentation.compose.search.model.UiSortPeriod
import com.git.trendingrepositories.presentation.compose.search.model.UiSortPeriod.Companion.getNext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repositoriesUseCase: GetRepositoriesUseCase
) :
    ViewModel() {
    private val _viewState = MutableStateFlow(
        SearchState(
            screenTitle = UiSortPeriod.LastDay.title,
            repositories = getData(UiSortPeriod.LastDay.sortPeriod),
            sortPeriod = UiSortPeriod.LastDay
        )
    )

    val viewState = _viewState.asStateFlow()

    private fun getData(period: SortPeriod):Flow<PagingData<Repository>> = repositoriesUseCase.getRepositoriesLocalFlow(period)
        .map { pagingData ->
            pagingData.map {
                it.toRepository()
            }
        }
        .cachedIn(viewModelScope)

    fun handleAction(actions: SearchActions) {
        when (actions) {
            SearchActions.ChangePeriod -> {
                val period = _viewState.value.sortPeriod.getNext()
                val response = getData(period.sortPeriod)

                _viewState.update { state ->
                    state.copy(
                        repositories = response,
                        screenTitle = period.title,
                        sortPeriod = period
                    )
                }
            }
        }
    }
}

sealed class SearchActions {
    object ChangePeriod : SearchActions()
}

data class SearchState(
    val sortPeriod: UiSortPeriod,
    val screenTitle: Int,
    val repositories: Flow<PagingData<Repository>>
)

