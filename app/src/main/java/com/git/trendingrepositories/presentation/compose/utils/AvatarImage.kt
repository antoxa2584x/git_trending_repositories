@file:OptIn(ExperimentalGlideComposeApi::class)

package com.git.trendingrepositories.presentation.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.git.trendingrepositories.R

val placeholder = placeholder(R.drawable.baseline_person_24)

@Composable
fun AvatarImage(
    url: String?,
) {
    GlideImage(
        model = url,
        contentDescription = "Avatar",
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        loading = placeholder,
        failure = placeholder,
    )
}
