package com.git.trendingrepositories.presentation.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun GitAppTheme(content: @Composable () -> Unit) {
    return MaterialTheme(
        content = content
    )
}