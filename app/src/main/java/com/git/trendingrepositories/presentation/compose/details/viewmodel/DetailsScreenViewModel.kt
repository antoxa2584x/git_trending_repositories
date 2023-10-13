package com.git.trendingrepositories.presentation.compose.details.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class DetailsScreenViewModel @Inject constructor() : ViewModel() {
    private val _viewState = MutableStateFlow(
        DetailsScreensState(isFavorite = false)
    )

    val viewState = _viewState.asStateFlow()

    fun handleAction(actions: DetailsActions) {
        when (actions) {
            DetailsActions.MarkAsFavorite -> _viewState.update { state ->
                state.copy(
                    isFavorite = state.isFavorite.not()
                )
            }
        }
    }
}

sealed class DetailsActions {
    object MarkAsFavorite : DetailsActions()
}

data class DetailsScreensState(
    val isFavorite: Boolean
)