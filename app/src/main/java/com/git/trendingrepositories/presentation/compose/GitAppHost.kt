package com.git.trendingrepositories.presentation.compose

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.git.trendingrepositories.presentation.compose.details.DetailsScreen
import com.git.trendingrepositories.presentation.compose.liked.LikedScreen
import com.git.trendingrepositories.presentation.compose.search.SearchScreen
import com.git.trendingrepositories.presentation.compose.viewmodel.SharedScreensViewModel

@Composable
fun GitAppHost() {
    GitAppNavHost(navHostController = rememberNavController(), viewModel = hiltViewModel())
}

@Composable
private fun GitAppNavHost(navHostController: NavHostController, viewModel: SharedScreensViewModel) {
    NavHost(navController = navHostController, startDestination = SEARCH_SCREEN,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }) {
        composable(SEARCH_SCREEN) {
            SearchScreen(onReposClick = {
                viewModel.repository = it

                navHostController.navigate(route = DETAILS_SCREEN)
            }, onFavoritesClick = {
                navHostController.navigate(route = FAVORITES_SCREEN)
            })
        }
        composable(DETAILS_SCREEN) {
            DetailsScreen(repository = viewModel.repository) {
                navHostController.navigateUp()
            }
        }
        composable(FAVORITES_SCREEN) {
            LikedScreen(
                onReposClick = {
                    viewModel.repository = it
                    navHostController.navigate(route = DETAILS_SCREEN)
                },
                onBackPressed = {
                    navHostController.navigateUp()
                })
        }
    }
}