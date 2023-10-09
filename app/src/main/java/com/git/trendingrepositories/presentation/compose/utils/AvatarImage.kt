@file:OptIn(ExperimentalGlideComposeApi::class)

package com.git.trendingrepositories.presentation.compose.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.git.trendingrepositories.R

val placeholder = placeholder(R.drawable.baseline_person_24)

@Composable
fun AvatarImage(
    url: String?,
    modifier: Modifier,
) {
    GlideImage(
        model = url,
        modifier = modifier.size(48.dp)
            .border(1.dp, Color.White, CircleShape)
            .clip(CircleShape),
        contentDescription = "Avatar",
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        loading = placeholder,
        failure = placeholder,
    )
}
