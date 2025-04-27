package com.example.mvvm.ui.navigation

import android.preference.PreferenceActivity.Header
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.domain.presentation.HomeStatus
import com.example.domain.presentation.Route
import com.example.feature.screen.home.Home
import com.example.feature.screen.home.HomeAction
import com.example.feature.screen.home.HomeState
import com.example.feature.screen.home.HomeViewModel
import com.example.feature.screen.tododetail.TodoDetail
import kotlin.collections.map

@Composable
fun Navigation() {
    val navHostController = rememberNavController()
    val homeViewModel: HomeViewModel = viewModel()
    val tabLabels = homeViewModel.homeState.directories.map { it.directory.label }
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    var homeStatus by remember { mutableStateOf(HomeStatus.DEFAULT) }
    val routeName =
        navBackStackEntry
            ?.destination
            ?.route
            ?.substringAfterLast(".") // 最後のドット以降を取得

    val onNavigate: (Route) -> Unit = { navHostController.navigate(it) }
    val onBackNavigate: () -> Unit = { navHostController.navigate(Route.Home) }

    LaunchedEffect(routeName) {
        Log.d("routeName", routeName.toString())
    }

    LaunchedEffect(Unit) {
        homeViewModel.onAction(HomeAction.GetAllDirectoryWithTasks)
    }

    Nav(
        routeName = routeName,
        tabLabels = tabLabels,
        homeState = homeViewModel.homeState,
        homeStatus = homeStatus,
        onClick = { homeStatus = it },
        onHomeAction = homeViewModel::onAction,
        onBackNavigate = onBackNavigate,
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Route.Home,
        ) {
            composable<Route.Home> {
                Home(
                    homeState = homeViewModel.homeState,
                    homeStatus = homeStatus,
                    directories = homeViewModel.homeState.directories,
                    tabIndex = homeViewModel.homeState.selectedDirectoryIndex,
                    onNavigate = onNavigate,
                    onHomeAction = homeViewModel::onAction,
                    onUpdateHomeStatus = { homeStatus = it },
                )
            }
            composable<Route.TodoDetail> {
                val params = it.toRoute<Route.TodoDetail>()
                val tasks = homeViewModel.homeState.directories[homeViewModel.homeState.selectedDirectoryIndex].tasks
                if (tasks.isNotEmpty()) {
                    val task = tasks.first { it.id == params.taskId }
                    TodoDetail(todo = task)
                }
            }
        }
    }
}

@Composable
private fun Nav(
    routeName: String?,
    homeState: HomeState,
    homeStatus: HomeStatus,
    tabLabels: List<String>,
    onBackNavigate: () -> Unit,
    onClick: (HomeStatus) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    content: @Composable () -> Unit,
) {
    val route: Route =
        when (routeName) {
            Route.Home.toString() -> Route.Home
            "TodoDetail/{taskId}" -> Route.TodoDetail(0)
            else -> Route.Home
        }

    Scaffold(
        topBar = {
            Header(
                route = route,
                labels = tabLabels,
                tabIndex = homeState.selectedDirectoryIndex,
                onTabSelected = {
                    onHomeAction(
                        HomeAction.SelectDirectory(it),
                    )
                },
                onBackNavigate = onBackNavigate,
            )
        },
        floatingActionButton = { Fab(route = route, homeState = homeState, homeStatus = homeStatus, onClick = onClick) },
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(it),
        ) {
            content()
        }
    }
}

@Composable
@Preview
private fun NavigationPreview() {
    val tabLabels = listOf("aaa", "bbb", "ccc")
    Nav(
        routeName = Route.Home.toString(),
        tabLabels = tabLabels,
        homeStatus = HomeStatus.DEFAULT,
        onBackNavigate = {},
        onClick = {},
        content = {},
        homeState = HomeState(),
        onHomeAction = {},
    )
}
