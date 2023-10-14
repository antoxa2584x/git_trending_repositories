package com.git.trendingrepositories.presentation.compose.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.git.trendingrepositories.domain.model.search.Repository
import com.git.trendingrepositories.domain.usecase.search.GetLikedUseCase
import com.git.trendingrepositories.domain.usecase.search.UpdateLikedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val updateLikedUseCase: UpdateLikedUseCase,
    private val getLikedUseCase: GetLikedUseCase
) :
    ViewModel() {
    private val _viewState = MutableStateFlow(
        DetailsScreensState(repository = null, isLiked = false)
    )

    val viewState = _viewState.asStateFlow()

    fun handleAction(actions: DetailsActions) {
        when (actions) {
            is DetailsActions.SetRepository -> viewModelScope.launch {
                _viewState.update { state ->
                    state.copy(
                        repository = actions.repository,
                        isLiked = getLikedUseCase.getLiked(actions.repository)?.isLiked ?: false
                    )
                }
            }

            is DetailsActions.MarkAsFavorite -> {
                _viewState.update { state ->
                    viewModelScope.launch {
                        state.repository?.let { updateLikedUseCase.updateLiked(!state.isLiked, it) }
                    }

                    state.copy(
                        isLiked = !state.isLiked
                    )
                }
            }
        }
    }
}

sealed class DetailsActions {
    data class MarkAsFavorite(val wasLiked: Boolean) : DetailsActions()
    data class SetRepository(val repository: Repository) : DetailsActions()
}

data class DetailsScreensState(
    val repository: Repository?,
    val isLiked: Boolean
)