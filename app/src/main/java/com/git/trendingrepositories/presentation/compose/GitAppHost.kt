package com.git.trendingrepositories.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.git.trendingrepositories.presentation.compose.details.DetailsScreen
import com.git.trendingrepositories.presentation.compose.search.SearchScreen
import com.git.trendingrepositories.presentation.compose.viewmodel.SharedScreensViewModel

@Composable
fun GitAppHost() {
    GitAppNavHost(navHostController = rememberNavController(), viewModel = hiltViewModel())
}

@Composable
private fun GitAppNavHost(navHostController: NavHostController, viewModel: SharedScreensViewModel) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    NavHost(navController = navHostController, startDestination = SEARCH_SCREEN) {
        composable(SEARCH_SCREEN) {
            SearchScreen(onReposClick = {
                viewModel.setSharedRepository(it)
                navHostController.navigate(route = DETAILS_SCREEN)
            }, onFavoritesClick = {
                navHostController.navigate(route = DETAILS_SCREEN)
            })
        }
        composable(DETAILS_SCREEN) {
            DetailsScreen(repository = viewState.sharedRepository) {
                navHostController.navigateUp()
            }
        }
        composable(FAVORITES_SCREEN) {
            SearchScreen()
        }
    }
}