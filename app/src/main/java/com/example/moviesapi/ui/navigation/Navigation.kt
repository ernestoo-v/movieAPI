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
import com.example.moviesapi.ui.screens.movie.popularMovies.MoviesPopularViewModel
import com.example.moviesapi.ui.screens.movie.movieDetail.MovieDetailScreen
import com.example.moviesapi.ui.screens.movie.movieDetail.MovieDetailViewModel
import com.example.moviesapi.ui.screens.movie.movieDiscover.MovieDiscoverViewModel
import com.example.moviesapi.ui.screens.personDetail.PersonDetailScreen
import com.example.moviesapi.ui.screens.personDetail.PersonDetailScreenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    moviesPopularViewModel: MoviesPopularViewModel,
    movieDiscoverViewModel: MovieDiscoverViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavItem.MainTab.createMainNavRoute(tabId = 0)
    ) {
        addDestinations(navController, moviesPopularViewModel, movieDiscoverViewModel)
    }
    /* NavigationBarMolecule(tabBarItems = tabBarItems, onclick = {
         navController.navigate(it)
     })*/
}

@RequiresApi(Build.VERSION_CODES.O)
private fun NavGraphBuilder.addDestinations(
    navController: NavHostController,
    moviesPopularViewModel: MoviesPopularViewModel,
    movieDiscoverViewModel: MovieDiscoverViewModel
) {
    composable(
        route = NavItem.MainTab.route,
        arguments = NavItem.MainTab.args
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt(NavArg.TabId.key)
        id?.let { tabNumber ->
            MainScreen(
                moviesPopularViewModel = moviesPopularViewModel,
                movieDiscoverViewModel = movieDiscoverViewModel,
                pageNumber = 1,
                onPageClick = { nextPageNumber ->
                    navController.navigate(NavItem.MainPages.createNavRoute(pageId = nextPageNumber))
                },
                onMovieClick = { movieId ->
                    navController.navigate(NavItem.Detail.createNavRouteDetail(movieId = movieId))
                },
                onBottomAppBarClick = { titleRoute, selectedTabIndexL ->
                    navController.navigate(NavItem.MainTab.createMainNavRoute(tabId = selectedTabIndexL))
                },
                selectedTabIndex = tabNumber,
                onUpClick = { navController.popBackStack() },
            )
        }
    }
    composable(
        route = NavItem.MainPages.route,
        arguments = NavItem.MainPages.args
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt(NavArg.PageId.key)
        id?.let { pageNumber ->
            MainScreen(
                moviesPopularViewModel = moviesPopularViewModel,
                movieDiscoverViewModel = movieDiscoverViewModel,
                pageNumber = pageNumber,
                onPageClick = { nextPageNumber ->
                    navController.navigate(NavItem.MainPages.createNavRoute(pageId = nextPageNumber))
                },
                onMovieClick = { movieId ->
                    navController.navigate(NavItem.Detail.createNavRouteDetail(movieId = movieId))
                },
                onBottomAppBarClick = { titleRoute, selectedTabIndexL ->
                    navController.navigate(NavItem.MainTab.createMainNavRoute(tabId = selectedTabIndexL))
                },
                selectedTabIndex = 0,
                onUpClick = { navController.popBackStack() },
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
