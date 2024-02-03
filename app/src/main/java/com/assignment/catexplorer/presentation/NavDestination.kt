package com.assignment.catexplorer.presentation

sealed class NavDestination(val route: String) {
    data object Home : NavDestination("home")
    data object Detail : NavDestination("detail/{catId}") {
        fun createRoute(catId: String) = "detail/${catId}"
    }
}