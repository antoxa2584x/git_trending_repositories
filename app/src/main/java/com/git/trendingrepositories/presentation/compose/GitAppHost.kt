package com.git.trendingrepositories.presentation.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.git.trendingrepositories.presentation.compose.search.SearchScreen

private const val SEARCH_SCREEN = "search_screen"

@Composable
fun GitAppHost() {
    GitAppNavHost(navHostController = rememberNavController())
}

@Composable
private fun GitAppNavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = SEARCH_SCREEN) {
        composable(SEARCH_SCREEN) {
            SearchScreen()
        }
    }
}