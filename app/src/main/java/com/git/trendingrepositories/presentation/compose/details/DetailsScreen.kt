package com.git.trendingrepositories.presentation.compose.details

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.git.trendingrepositories.R
import com.git.trendingrepositories.data.remote.search.model.Repository
import com.git.trendingrepositories.presentation.compose.details.viewmodel.DetailsActions
import com.git.trendingrepositories.presentation.compose.details.viewmodel.DetailsScreenViewModel
import com.git.trendingrepositories.presentation.compose.theme.SurfaceColor
import com.git.trendingrepositories.presentation.compose.utils.AvatarImage


@Preview
@Composable
fun DetailsScreen(
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    repository: Repository = Repository(),
    onBackPressed: () -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    DetailsScreenContent(repository = repository,
        isFavorite = state.isFavorite,
        onBackPressed = onBackPressed,
        markAsFavorite = {
            viewModel.handleAction(DetailsActions.MarkAsFavorite)
        },
        onWebPressed = {
            uriHandler.openUri(it ?: "")
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreenContent(
    repository: Repository,
    isFavorite: Boolean,
    onBackPressed: () -> Unit,
    markAsFavorite: () -> Unit,
    onWebPressed: (url: String?) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
            title = {
                Text(text = stringResource(R.string.repository))
            },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = { onWebPressed(repository.htmlUrl) }) {
                    Icon(
                        tint = Color.Blue,
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_explore_24),
                        contentDescription = "Web"
                    )
                }
            })
    },
        content = { DetailsScreenContent(repository, it) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                contentColor = Color.White,
                onClick = { markAsFavorite() },
            ) {
                Crossfade(targetState = isFavorite, label = "") { isFavorite ->
                    Icon(
                        imageVector = ImageVector.vectorResource(if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24),
                        contentDescription = "Mark Star"
                    )
                }
            }
        })
}

@Composable
fun DetailsScreenContent(repository: Repository, paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            AboutBlock(repository)

            if (repository.description.isNullOrBlank().not())
                Text(
                    text = repository.getLongDescription(),
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, color = Color.DarkGray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(SurfaceColor)
                        .padding(16.dp),
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
        }
    }
}

@Composable
private fun AboutBlock(repository: Repository) {
    Column(
        Modifier
            .padding(bottom = 16.dp)
            .border(
                BorderStroke(1.dp, color = Color.DarkGray), shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .background(SurfaceColor)
            .padding(16.dp)
    ) {
        Row {
            AvatarImage(
                url = repository.owner.avatarUrl,
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .align(CenterVertically)
            )
            Text(
                text = repository.owner.login,
                modifier = Modifier
                    .wrapContentSize()
                    .align(CenterVertically)
                    .padding(horizontal = 16.dp),
                fontSize = 18.sp
            )
        }
        Text(
            text = repository.name,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 16.dp),
            fontWeight = FontWeight.Bold,
        )

        Row {
            Text(
                text = "Created at: ",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 4.dp),
                fontSize = 14.sp
            )
            Text(
                text = repository.getSimpleDate(),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 4.dp),
                fontSize = 14.sp,
                color = Color.LightGray
            )
        }

        if (repository.language.isNullOrBlank().not()) Row {
            Text(
                text = "Language: ",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 4.dp),
                fontSize = 14.sp
            )
            Text(
                text = repository.language ?: "",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 4.dp),
                fontSize = 14.sp,
                color = Color.LightGray
            )
        }

        CountsRow(stars = repository.stargazersCount, forks = repository.forksCount)
    }
}

@Composable
private fun CountsRow(stars: Int, forks: Int) {

    val tint = Color.Gray

    Row(Modifier.padding(top = 16.dp)) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "star",
            Modifier
                .width(12.dp)
                .height(12.dp)
                .align(CenterVertically),
            tint = tint,
        )

        Text(
            text = "$stars ${stringResource(id = R.string.stars)}",
            modifier = Modifier.wrapContentSize(),
            color = tint,
            fontSize = 12.sp
        )

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.git_fork),
            contentDescription = "star",
            Modifier
                .padding(start = 24.dp)
                .width(12.dp)
                .height(12.dp)
                .align(CenterVertically),
            tint = tint,
        )

        Text(
            text = "$forks ${stringResource(id = R.string.forks)}",
            modifier = Modifier.wrapContentSize(),
            color = tint,
            fontSize = 12.sp
        )
    }
}

