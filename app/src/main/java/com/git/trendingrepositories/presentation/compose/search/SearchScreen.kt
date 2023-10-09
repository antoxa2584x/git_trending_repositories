package com.git.trendingrepositories.presentation.compose.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.git.trendingrepositories.R
import com.git.trendingrepositories.api.search.model.Repository
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchActions
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchScreenViewModel
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchState

@Preview
@Composable
fun SearchScreen(viewModel: SearchScreenViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    SearchScreen(state) {
        viewModel.handleAction(actions = SearchActions.ChangePeriod)
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
                Text(text = "${stringResource(R.string.trending)} - ${stringResource(state.screenTitle)}")
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
private fun SearchScreenContent(
    state: SearchState, padding: PaddingValues
) {
    val searchData = state.repositories.collectAsLazyPagingItems()
    val isLoading = searchData.loadState.refresh is LoadState.Loading

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        when {
            isLoading -> CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )

            !isLoading && searchData.itemCount == 0 -> Box(Modifier.fillMaxSize()) {
                //Because we not handle error, could be just empty list on 403
                Text(
                    text = stringResource(R.string.empty_data_or_load_error),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> SearchList(data = searchData)
        }
    }
}

@Composable
private fun SearchList(data: LazyPagingItems<Repository>) {
    val state = rememberLazyListState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
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