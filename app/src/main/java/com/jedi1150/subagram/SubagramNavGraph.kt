package com.jedi1150.subagram

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jedi1150.subagram.ui.Screen
import com.jedi1150.subagram.ui.createWord.CreateWordRoute
import com.jedi1150.subagram.ui.home.HomeRoute
import com.jedi1150.subagram.ui.home.HomeViewModel
import com.jedi1150.subagram.ui.settings.SettingsRoute
import com.jedi1150.subagram.ui.word.WordRoute
import com.jedi1150.subagram.ui.word.WordViewModel

@Composable
fun SubagramNavGraph(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route,
    onRouteChanged: (Screen) -> Unit,
    contentPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(Screen.Home.route) { navBackStackEntry ->
            onRouteChanged(Screen.Home)
            HomeRoute(
                viewModel = homeViewModel,
                contentPadding = contentPadding,
                navigateToCreateWord = {
                    navController.navigate(Screen.CreateWord.route) {
                        launchSingleTop = true
                    }
                },
                navigateToWord = { word ->
                    navController.navigate(Screen.Word(word.value).route + "/${word.uid}") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(Screen.CreateWord.route) { navBackStackEntry ->
            onRouteChanged(Screen.CreateWord)
            CreateWordRoute(
                viewModel = homeViewModel,
                contentPadding = contentPadding,
                navigateToWord = { word ->
                    navController.navigate(Screen.Word(word.value).route + "/${word.uid}") {
                        popUpTo(Screen.Home.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
        composable(
            Screen.Word().route + "/{uid}",
            arguments = listOf(
                navArgument("uid") { type = NavType.LongType }
            ),
        ) { navBackStackEntry ->
            val viewModel = hiltViewModel<WordViewModel>()

            onRouteChanged(Screen.Word(viewModel.uiState.currentWord.value))
            WordRoute(viewModel = viewModel, contentPadding = contentPadding)
        }
        composable(Screen.Settings.route) { navBackStackEntry ->
            onRouteChanged(Screen.Settings)
            SettingsRoute(contentPadding = contentPadding)
        }
    }
}
