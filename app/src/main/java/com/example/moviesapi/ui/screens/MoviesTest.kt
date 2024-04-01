package com.example.moviesapi.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moviesapi.ui.navigation.navigationBar.NavigationBarMolecule
import com.example.moviesapi.ui.navigation.navigationBar.TabBarItem
import com.example.moviesapi.ui.theme.MoviesAPITheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoviesTest(
    onBottomAppBarClick: (titleRoute: String, selectedTabIndexL: Int) -> Unit

) {
    // setting up the individual tabs
    val mainTab = TabBarItem(
        title = "Main",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    val moviesTab = TabBarItem(
        title = "Movies",
        selectedIcon = Icons.Filled.Movie,
        unselectedIcon = Icons.Outlined.Movie
    )
    val userTab = TabBarItem(
        title = "User",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
    // creating a list of all the tabs
    val tabBarItems = listOf(mainTab, moviesTab, userTab)
    MoviesAPITheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = {
                    NavigationBarMolecule(
                        tabBarItems = tabBarItems,
                        onBottomAppBarClick = onBottomAppBarClick,
                        selectedTabIndex = 1
                    )
                }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Thing 1")
                    Text("Thing 2")
                    Text("Thing 3")
                    Text("Thing 4")
                    Text("Thing 5")
                }
            }
        }
    }
}