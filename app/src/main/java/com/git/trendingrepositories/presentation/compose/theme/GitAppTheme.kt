package com.git.trendingrepositories.presentation.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun GitAppTheme(content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.apply {
        setStatusBarColor(
            color = Color.Black
        )
        navigationBarDarkContentEnabled = true
    }

    return MaterialTheme(
        content = content,
        colorScheme = darkColorScheme(surface = SurfaceColor)
    )
}