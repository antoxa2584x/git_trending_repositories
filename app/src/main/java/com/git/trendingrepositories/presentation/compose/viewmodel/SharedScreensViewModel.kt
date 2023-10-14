package com.git.trendingrepositories.presentation.compose.viewmodel

import androidx.lifecycle.ViewModel
import com.git.trendingrepositories.data.remote.search.model.RepositoryDto
import com.git.trendingrepositories.domain.model.search.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SharedScreensViewModel @Inject constructor() :
    ViewModel() {
    private val _viewState = MutableStateFlow(
        SharedScreensState(sharedRepository = null)
    )

    val viewState = _viewState.asStateFlow()

    fun setSharedRepository(repository: Repository){
        _viewState.update { state ->
            state.copy(
                sharedRepository = repository
            )
        }
    }
}

data class SharedScreensState(
    val sharedRepository: Repository?
)