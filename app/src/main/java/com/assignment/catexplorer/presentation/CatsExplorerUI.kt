package com.assignment.catexplorer.presentation

import CatsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import com.assignment.catexplorer.domain.model.CatBreedEntity
import com.assignment.catexplorer.presentation.catdetails.CatBreedDetailState
import com.assignment.catexplorer.presentation.catdetails.CatDetailsScreen

@Composable
fun CatsExplorerUI(
    lazyPagingItems: LazyPagingItems<CatBreedEntity>,
    fetchCatBreedDetail: (id: String) -> Unit,
    catDetailsState: CatBreedDetailState,
) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = NavDestination.Home.route) {

        composable(route = NavDestination.Home.route) {
            CatsScreen(
                cats = lazyPagingItems,
                onItemClick = { catId ->
                    navController.navigate(NavDestination.Detail.createRoute(catId))
                }
            )
        }

        composable(route = NavDestination.Detail.route) { backStackEntry ->
            val catId = backStackEntry.arguments?.getString("catId")

            CatDetailsScreen(
                fetchCatBreedDetail = fetchCatBreedDetail,
                catBreedId = catId,
                catDetailsState = catDetailsState,
                onBack = { navController.navigateUp() }
            )
        }
    }
}