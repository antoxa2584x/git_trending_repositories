package com.git.trendingrepositories.presentation.compose.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchScreenViewModel

@Composable
fun SearchScreen(viewModel: SearchScreenViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    //TODO
}