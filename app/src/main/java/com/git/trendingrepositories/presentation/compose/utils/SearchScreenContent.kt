package com.git.trendingrepositories.presentation.compose.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.git.trendingrepositories.R
import com.git.trendingrepositories.domain.model.search.Repository
import kotlinx.coroutines.delay

@Composable
fun SearchScreenContent(
    searchData: LazyPagingItems<Repository>,
    isRemoteContent: Boolean = true,
    onReposClick: (repo: Repository) -> Unit
) {
    val isLoading = searchData.loadState.refresh is LoadState.Loading
    val isError = searchData.loadState.refresh is LoadState.Error

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            isLoading -> CircularProgressIndicator(
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 24.dp)
            )

            isError -> Box(Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 24.dp)
                ) {
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

            !isLoading && searchData.itemCount == 0 -> {
                var show by remember { mutableStateOf(false) }

                LaunchedEffect(this) {
                    delay(100)
                    show = true
                }

                if (show)
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 24.dp),
                        text = stringResource(R.string.empty_results)
                    )
            }

            else -> SearchList(
                data = searchData, isRemoteContent = isRemoteContent, onReposClick = onReposClick
            )
        }
    }
}

@Composable
private fun SearchList(
    data: LazyPagingItems<Repository>,
    onReposClick: (repo: Repository) -> Unit,
    isRemoteContent: Boolean
) {
    val isMoreLoading = data.loadState.append is LoadState.Loading
    val isError = data.loadState.append is LoadState.Error

    Box {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
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
                    RepositoryItem(repo = it) {
                        onReposClick(repository)
                    }
                }
            }

            if (isRemoteContent)
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