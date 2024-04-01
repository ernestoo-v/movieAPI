package com.example.moviesapi.ui.screens.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import com.example.moviesapi.ui.navigation.navigationBar.TabBarItem

object TabBarItems {
    val mainTab = TabBarItem(
        title = "Movies",
        selectedIcon = Icons.Filled.Movie,
        unselectedIcon = Icons.Outlined.Movie
    )
    val moviesTab = TabBarItem(
        title = "Discover",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    )
    val userTab = TabBarItem(
        title = "User",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
    val tabBarItems = listOf(mainTab, moviesTab, userTab)
}