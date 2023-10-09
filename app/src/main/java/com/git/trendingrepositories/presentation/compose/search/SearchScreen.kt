package com.git.trendingrepositories.presentation.compose.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.git.trendingrepositories.R
import com.git.trendingrepositories.api.search.model.Repository
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchScreenItem
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchScreenViewModel
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchState

@Composable
fun SearchScreen(viewModel: SearchScreenViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    SearchScreen(state) {
        //TODO Handle clicks
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    state: SearchState, onFilterChangeClick: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
            title = {
                Text(text = "${stringResource(R.string.git_search)} - ${stringResource(state.screenTitle)}")
            })
    }, content = { padding ->
        SearchScreenContent(state, padding)
    },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                contentColor = Color.Blue,
                onClick = { onFilterChangeClick.invoke() },
            ) {
                Image(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(
                        Color.White
                    ),
                )
            }
        })
}

@Composable
private fun SearchScreenContent(state: SearchState, padding: PaddingValues) {
    val searchData = state.repositories.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        SearchList(data = searchData)
    }
}

@Composable
private fun SearchList(data: LazyPagingItems<Repository>) {
    val state = rememberLazyListState()

    LazyColumn(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(count = data.itemCount, key = { index ->
            val repository = data[index]
            "${repository?.id}${index}"
        }) { index ->
            val repository = data[index]
            SearchScreenItem(repo = repository ?: Repository()) {
                //TODO Handle repo click
            }
        }
    }
}
