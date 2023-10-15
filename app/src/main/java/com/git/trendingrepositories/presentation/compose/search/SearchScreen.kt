package com.git.trendingrepositories.presentation.compose.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.git.trendingrepositories.R
import com.git.trendingrepositories.domain.model.search.Repository
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchActions
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchScreenViewModel
import com.git.trendingrepositories.presentation.compose.search.viewmodel.SearchState
import com.git.trendingrepositories.presentation.compose.utils.SearchScreenContent
import com.git.trendingrepositories.presentation.compose.utils.SearchView
import kotlin.reflect.KFunction1

@Preview
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    onReposClick: (repo: Repository) -> Unit = { },
    onFavoritesClick: () -> Unit = { },
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsState()

    SearchScreen(state = state,
        searchState = searchText,
        onSearchChange = viewModel::onSearchTextChange,
        onFilterChangeClick = {
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
    onFavoritesClick: () -> Unit,
    searchState: String,
    onSearchChange: KFunction1<String, Unit>
) {
    val searchData = state.repositories.collectAsLazyPagingItems()

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SearchView(
                value = searchState,
                onSearchChange = onSearchChange
            )

            SearchScreenContent(
                searchData = searchData,
                onReposClick = onReposClick,
                isRemoteContent = true
            )
        }

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
