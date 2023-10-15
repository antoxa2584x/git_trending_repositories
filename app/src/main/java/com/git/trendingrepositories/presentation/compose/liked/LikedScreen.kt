package com.git.trendingrepositories.presentation.compose.liked

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.git.trendingrepositories.R
import com.git.trendingrepositories.domain.model.search.Repository
import com.git.trendingrepositories.presentation.compose.liked.viewmodel.LikeScreenState
import com.git.trendingrepositories.presentation.compose.liked.viewmodel.LikeScreenViewModel
import com.git.trendingrepositories.presentation.compose.utils.SearchScreenContent
import com.git.trendingrepositories.presentation.compose.utils.SearchView
import kotlin.reflect.KFunction1


@Composable
fun LikedScreen(
    viewModel: LikeScreenViewModel = hiltViewModel(),
    onReposClick: (repo: Repository) -> Unit = {},
    onBackPressed: () -> Unit = {},
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsState()

    LikedScreen(
        state = state,
        searchState = searchText,
        onSearchChange = viewModel::onSearchTextChange,
        onReposClick = onReposClick, onBackPressed = onBackPressed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LikedScreen(
    state: LikeScreenState,
    onReposClick: (repo: Repository) -> Unit,
    onBackPressed: () -> Unit,
    searchState: String,
    onSearchChange: KFunction1<String, Unit>
) {
    val data = state.repositories.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                title = {
                    Text(text = stringResource(R.string.saved))
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"
                        )
                    }
                }
            )
        },

        content = { padding ->
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
                    searchData = data,
                    onReposClick = onReposClick,
                    emptyDataText = stringResource(if (searchState.isNotEmpty()) R.string.empty_results else R.string.no_saved_repositories_yet),
                    isRemoteContent = true
                )
            }
        },
    )
}
