package com.git.trendingrepositories.presentation.compose.liked.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.git.trendingrepositories.data.mappers.toRepository
import com.git.trendingrepositories.domain.model.search.Repository
import com.git.trendingrepositories.domain.usecase.search.GetLikedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikeScreenViewModel @Inject constructor(private val getLikedUseCase: GetLikedUseCase) :
    ViewModel() {
    private val _viewState = MutableStateFlow(
        LikeScreenState(
            repositories = getData(""),
        )
    )

    val viewState = _viewState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun onSearchTextChange(text: String) {
        _searchText.value = text

        viewModelScope.launch {
            _searchText.debounce {
                500L
            }.collectLatest {
                val response = getData(text)

                _viewState.update { state ->
                    state.copy(
                        repositories = response,
                    )
                }
            }
        }
    }

    private fun getData(query: String) =
        getLikedUseCase.getLiked(query).map { it.map { it.toRepository() } }.cachedIn(viewModelScope)
}

data class LikeScreenState(val repositories: Flow<PagingData<Repository>>)