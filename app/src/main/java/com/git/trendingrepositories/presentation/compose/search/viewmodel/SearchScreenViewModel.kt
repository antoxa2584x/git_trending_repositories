package com.git.trendingrepositories.presentation.compose.search.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor() : ViewModel() {
    private val _viewState = MutableStateFlow(SearchState())
    val viewState = _viewState.asStateFlow()
}

sealed class SearchActions {

}

class SearchState() //TODO

