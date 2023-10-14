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
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class LikeScreenViewModel @Inject constructor(private val getLikedUseCase: GetLikedUseCase) :
    ViewModel() {
    private val _viewState = MutableStateFlow(
        LikeScreenState(
            repositories = getDate(),
        )
    )

    val viewState = _viewState.asStateFlow()

    private fun getDate() =
        getLikedUseCase.getLiked().map { it.map { it.toRepository() } }.cachedIn(viewModelScope)
}

data class LikeScreenState(val repositories: Flow<PagingData<Repository>>)