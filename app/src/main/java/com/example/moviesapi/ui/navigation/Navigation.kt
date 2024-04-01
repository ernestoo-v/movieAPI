package com.example.moviesapi.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesapi.ui.screens.mainScreen.MainScreen
import com.example.moviesapi.ui.screens.mainScreen.MainScreenViewModel
import com.example.moviesapi.ui.screens.movie.movieDetail.MovieDetailScreen
import com.example.moviesapi.ui.screens.movie.movieDetail.MovieDetailViewModel
import com.example.moviesapi.ui.screens.personDetail.PersonDetailScreen
import com.example.moviesapi.ui.screens.personDetail.PersonDetailScreenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(mainScreenViewModel: MainScreenViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavItem.Main.route) {
        addDestinations(navController, mainScreenViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun NavGraphBuilder.addDestinations(
    navController: NavHostController,
    mainScreenViewModel: MainScreenViewModel
) {
    composable(NavItem.Main.route) {
        MainScreen(
            mainScreenViewModel,
            1,
            onPageClick = { pageNumber ->
                navController.navigate(NavItem.MainPages.createNavRoute(pageId = pageNumber))
            },
            onMovieClick = { movieId ->
                navController.navigate(NavItem.Detail.createNavRouteDetail(movieId = movieId))
            }
        )
    }

    composable(
        route = NavItem.MainPages.route,
        arguments = NavItem.MainPages.args
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt(NavArg.PageId.key)
        id?.let { pageNumber ->
            MainScreen(
                mainScreenViewModel = mainScreenViewModel,
                pageNumber = pageNumber,
                onPageClick = { nextPageNumber ->
                    navController.navigate(NavItem.MainPages.createNavRoute(pageId = nextPageNumber))
                },
                onMovieClick = { movieId ->
                    navController.navigate(NavItem.Detail.createNavRouteDetail(movieId = movieId))
                }
            )
        }
    }

    composable(
        route = NavItem.Detail.route,
        arguments = NavItem.Detail.args
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt(NavArg.MovieId.key)
        id?.let { movieId ->
            MovieDetailScreen(
                movieDetailViewModel = MovieDetailViewModel(),
                movieId = movieId,
                onCastPersonClick = { personId ->
                    navController.navigate(
                        NavItem.CastPersonDetail.createNavRouteCastPersonDetail(personId = personId)
                    )
                },
                onUpClick = { navController.popBackStack() }
            )
        }
    }

    composable(
        route = NavItem.CastPersonDetail.route,
        arguments = NavItem.CastPersonDetail.args
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt(NavArg.PersonId.key)
        id?.let { personId ->
            PersonDetailScreen(
                personId = personId,
                personDetailScreenViewModel = PersonDetailScreenViewModel(),
                onUpClick = { navController.popBackStack() },
                onPersonMovieCreditsItemMovieClick = {
                    Log.d("Revisar", "Pelicla clicada2 ${it}")
                    navController.navigate(NavItem.Detail.createNavRouteDetail(movieId = it))
                }
            )
        }
    }
}
