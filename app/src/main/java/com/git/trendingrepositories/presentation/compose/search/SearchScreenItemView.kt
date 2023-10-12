package com.git.trendingrepositories.presentation.compose.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.git.trendingrepositories.api.search.model.Repository
import com.git.trendingrepositories.presentation.compose.utils.AvatarImage

@Composable
fun SearchScreenItem(repo: Repository, onClick: () -> Unit) {
    SearchScreenItem(
        ownerName = repo.owner.login,
        repoName = repo.name,
        avatarUrl = repo.owner.avatarUrl,
        description = repo.getShortDescription(),
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
    Surface(border = BorderStroke(1.dp, Color.DarkGray),
        shape = RoundedCornerShape(8.dp),
        onClick = { itemClick.invoke() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            AvatarImage(
                url = avatarUrl,
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .align(Alignment.CenterVertically)
            )
            Column(Modifier.padding(start = 16.dp)) {
                Row {
                    val textColor = Color(0xFF2F81F7)

                    Text(
                        text = "${ownerName}/",
                        modifier = Modifier.wrapContentSize(),
                        maxLines = 1,
                        color = textColor
                    )
                    Text(
                        text = repoName,
                        modifier = Modifier.wrapContentSize(),
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }
                if (description.isNotBlank()) Text(
                    text = description.trim(),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 4.dp),
                    maxLines = 2,
                    fontSize = 12.sp,
                    lineHeight = 13.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Row(Modifier.padding(top = 8.dp)) {
                    val tint = Color.Gray
                    Image(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(tint),
                        modifier = Modifier
                            .width(12.dp)
                            .height(12.dp)
                            .align(Alignment.CenterVertically)
                    )

                    Text(
                        text = starsCount.toString(),
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterVertically),
                        maxLines = 1,
                        fontSize = 12.sp,
                        color = tint
                    )
                }
            }
        }
    }
}