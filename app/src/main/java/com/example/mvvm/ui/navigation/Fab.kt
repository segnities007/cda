package com.example.mvvm.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.domain.presentation.FloatingActionButtonItem
import com.example.domain.presentation.HomeStatus
import com.example.domain.presentation.Route
import com.example.feature.component.buttons.multi_fabs.MultiFloatingActionButton
import com.example.feature.screen.home.HomeState
import com.example.mvvm.R
import kotlin.collections.isNotEmpty

@Composable
fun Fab(
    route: Route,
    homeState: HomeState,
    homeStatus: HomeStatus,
    onClick: (HomeStatus) -> Unit,
) {
    val items =
        when {
            homeState.directories.isNotEmpty() -> {
                listOf(
                    FloatingActionButtonItem(
                        icon = painterResource(R.drawable.baseline_note_add_24),
                        label = "create todo",
                        homeStatus = HomeStatus.CREATING_TASK,
                        onClick = onClick,
                    ),
                    FloatingActionButtonItem(
                        icon = painterResource(R.drawable.baseline_delete_24),
                        label = "delete todo",
                        homeStatus = HomeStatus.DELETING_TASK,
                        onClick = onClick,
                    ),
                    FloatingActionButtonItem(
                        icon = painterResource(R.drawable.baseline_create_new_folder_24),
                        label = "create directory",
                        homeStatus = HomeStatus.CREATING_DIRECTORY,
                        onClick = onClick,
                    ),
                    FloatingActionButtonItem(
                        icon = painterResource(R.drawable.baseline_folder_delete_24),
                        label = "delete directory",
                        homeStatus = HomeStatus.DELETING_DIRECTORY,
                        onClick = onClick,
                    ),
                )
            }
            else -> {
                listOf(
                    FloatingActionButtonItem(
                        icon = painterResource(R.drawable.baseline_create_new_folder_24),
                        label = "create directory",
                        homeStatus = HomeStatus.CREATING_DIRECTORY,
                        onClick = onClick,
                    ),
                    FloatingActionButtonItem(
                        icon = painterResource(R.drawable.baseline_folder_delete_24),
                        label = "delete directory",
                        homeStatus = HomeStatus.DELETING_DIRECTORY,
                        onClick = onClick,
                    ),
                )
            }
        }

    when (route) {
        Route.Home -> {
            MultiFloatingActionButton(
                fabIcon = painterResource(R.drawable.baseline_settings_24),
                items = items,
                homeStatus = homeStatus,
                onCollapsed = onClick,
            )
        }
        else -> { /*nothing*/ }
    }
}
