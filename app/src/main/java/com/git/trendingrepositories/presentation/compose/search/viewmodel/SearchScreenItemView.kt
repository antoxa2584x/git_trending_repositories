package com.git.trendingrepositories.presentation.compose.search.viewmodel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.git.trendingrepositories.api.search.model.Repository
import com.git.trendingrepositories.presentation.compose.utils.AvatarImage

@Composable
fun SearchScreenItem(repo: Repository, onClick: () -> Unit) {
    SearchScreenItem(
        ownerName = repo.owner.login,
        repoName = repo.name,
        avatarUrl = repo.owner.avatarUrl,
        description = repo.description ?: "",
        starsCount = repo.stargazersCount,
        itemClick = onClick
    )
}

@Composable
fun SearchScreenItem(
    ownerName: String,
    repoName: String,
    avatarUrl: String,
    description: String,
    starsCount: Int?,
    itemClick: () -> Unit
) {
    Surface(onClick = { itemClick.invoke() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AvatarImage(
                url = avatarUrl
            )
            Column() {
                Row {

                    Text(
                        text = "${ownerName}/",
                        modifier = Modifier.wrapContentSize(),
                        maxLines = 1,
                    )
                    Text(
                        text = repoName,
                        modifier = Modifier.wrapContentSize(),
                        maxLines = 1,
                    )
                }
                if (description.isNotBlank()) Text(
                    text = description.trim(),
                )
                Row() {
                    Image(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "",
                    )

                    Text(
                        text = starsCount.toString(),
                    )
                }
            }
        }
    }
}