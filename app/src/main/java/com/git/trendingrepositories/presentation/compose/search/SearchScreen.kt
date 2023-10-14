package com.git.trendingrepositories.presentation.compose.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.git.trendingrepositories.R
import com.git.trendingrepositories.domain.model.search.Repository
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchActions
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchScreenViewModel
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchState

@Preview
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    onReposClick: (repo: Repository) -> Unit = { },
    onFavoritesClick: () -> Unit = { }
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    SearchScreen(state = state, onFilterChangeClick = {
        viewModel.handleAction(actions = SearchActions.ChangePeriod)
    }, onReposClick = {
        onReposClick(it)
    }, onFavoritesClick = onFavoritesClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    state: SearchState,
    onFilterChangeClick: () -> Unit,
    onReposClick: (repo: Repository) -> Unit,
    onFavoritesClick: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
            title = {
                Text(text = "${stringResource(R.string.trending)} - ${stringResource(state.screenTitle)}")
            },
            actions = {
                IconButton(onClick = { onFavoritesClick() }) {
                    Icon(
                        tint = Blue,
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_favorite_24),
                        contentDescription = "Favorite"
                    )
                }
            })
    }, content = { padding ->
        SearchScreenContent(state, padding, onReposClick)
    },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                contentColor = White,
                onClick = { onFilterChangeClick.invoke() },
            ) {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = "DateRange"
                )
            }
        })
}

@Composable
private fun SearchScreenContent(
    state: SearchState, padding: PaddingValues, onReposClick: (repo: Repository) -> Unit
) {
    val searchData = state.repositories.collectAsLazyPagingItems()
    val isLoading = searchData.loadState.refresh is LoadState.Loading
    val isError = searchData.loadState.refresh is LoadState.Error

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        when {
            isLoading -> CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )

            isError -> Box(Modifier.fillMaxSize()) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(text = stringResource(R.string.load_error))
                    ClickableText(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp),
                        text = AnnotatedString(stringResource(R.string.retry)),
                        style = TextStyle(
                            color = Blue,
                            textDecoration = TextDecoration.Underline,
                            fontSize = 18.sp
                        ),
                        onClick = {
                            searchData.retry()
                        }
                    )
                }
            }

            else -> SearchList(
                data = searchData, onReposClick
            )
        }
    }
}

@Composable
private fun SearchList(
    data: LazyPagingItems<Repository>, onReposClick: (repo: Repository) -> Unit
) {
    val state = rememberLazyListState()
    val isMoreLoading = data.loadState.append is LoadState.Loading
    val isError = data.loadState.append is LoadState.Error

    Box {
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

                repository?.let {
                    SearchScreenItem(repo = it) {
                        onReposClick(repository)
                    }
                }
            }
            item {
                when {
                    isMoreLoading ->
                        Box(Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(
                                Modifier.align(Alignment.Center)
                            )
                        }

                    isError ->
                        Box(Modifier.fillMaxWidth()) {
                            ClickableText(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(8.dp),
                                text = AnnotatedString(stringResource(R.string.retry)),
                                style = TextStyle(
                                    color = Blue,
                                    textDecoration = TextDecoration.Underline,
                                    fontSize = 18.sp
                                ),
                                onClick = {
                                    data.retry()
                                }
                            )
                        }
                }
            }
        }
    }
}