package com.jedi1150.subagram

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jedi1150.subagram.ui.Screen
import com.jedi1150.subagram.ui.home.HomeViewModel
import com.jedi1150.subagram.ui.theme.SubagramTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SubagramApp() {
    val homeViewModel = hiltViewModel<HomeViewModel>()

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val wordsWithAnagrams by remember(homeViewModel.uiState.wordsWithAnagrams, lifecycleOwner) { homeViewModel.uiState.wordsWithAnagrams.flowWithLifecycle(lifecycleOwner.lifecycle) }.collectAsState(initial = emptyList())

    val navController = rememberNavController()
    val currentDestination by navController.currentBackStackEntryAsState()
    var currentRoute: Screen by remember { mutableStateOf(Screen.Home) }

    val showFab by remember { derivedStateOf { currentDestination?.destination?.route == Screen.Home.route && wordsWithAnagrams.isNotEmpty() } }

    SubagramTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        AnimatedContent(
                            targetState = currentRoute,
                            modifier = Modifier.fillMaxWidth(),
                            transitionSpec = {
                                fadeIn(animationSpec = tween()) with fadeOut(animationSpec = tween())
                            },
                            contentAlignment = Alignment.Center,
                        ) { route ->
                            Text(text = route.title, textAlign = TextAlign.Center)
                        }
                    },
                    modifier = Modifier
                        .statusBarsPadding()
                        .displayCutoutPadding(),
                    navigationIcon = {
                        Crossfade(targetState = currentDestination) { stackEntry ->
                            if (stackEntry?.destination?.route.toString() != Screen.Home.route) {
                                IconButton(
                                    onClick = {
                                        navController.navigateUp()
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack, contentDescription = null
                                    )
                                }
                            }
                        }
                    },
                    actions = {
                        Crossfade(targetState = currentDestination) { stackEntry ->
                            if (stackEntry?.destination?.route.toString() != Screen.Settings.route) {
                                IconButton(onClick = {
                                    navController.navigate(Screen.Settings.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }) {
                                    Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                                }
                            }
                        }
                    },
                )
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = showFab,
                    modifier = Modifier.sizeIn(
                        minWidth = 96.dp,
                        minHeight = 96.dp,
                    ),
                    enter = scaleIn(transformOrigin = TransformOrigin(0f, 0f)) + fadeIn() + expandIn(expandFrom = Alignment.TopStart),
                    exit = scaleOut(transformOrigin = TransformOrigin(0f, 0f)) + fadeOut() + shrinkOut(shrinkTowards = Alignment.TopStart),
                ) {
                    LargeFloatingActionButton(
                        onClick = {
                            navController.navigate(Screen.CreateWord.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
        ) { contentPadding ->
            SubagramNavGraph(
                homeViewModel = homeViewModel,
                navController = navController,
                startDestination = Screen.Home.route,
                onRouteChanged = { route -> currentRoute = route },
                contentPadding = contentPadding,
            )
        }
    }
}
