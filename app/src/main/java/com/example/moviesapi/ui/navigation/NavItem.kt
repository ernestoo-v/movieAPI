package com.example.moviesapi.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    val baseRoute: String,
    val navArgs: List<NavArg> = emptyList()
) {
    val route = run {
        //baseroute/{arg1}/{arg2}
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argKeys)
            .joinToString("/")
    }
    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

    object MainTab : NavItem("main", listOf(NavArg.TabId))
    object MainPages : NavItem("main2", listOf(NavArg.PageId))
    object Detail : NavItem("detail", listOf(NavArg.MovieId))
    object CastPersonDetail : NavItem("castPersonDetail", listOf(NavArg.PersonId))

    fun createMainNavRoute(tabId: Int) = "$baseRoute/$tabId"
    fun createNavRoute(pageId: Int) = "$baseRoute/$pageId"
    fun createNavRouteDetail(movieId: Int) = "$baseRoute/$movieId"
    fun createNavRouteCastPersonDetail(personId: Int) = "$baseRoute/$personId"
}

enum class NavArg(val key: String, val navType: NavType<*>) {
    TabId("tabId", NavType.IntType),
    PageId("pageId", NavType.IntType),
    MovieId("movieId", NavType.IntType),
    PersonId("personId", NavType.IntType)
}