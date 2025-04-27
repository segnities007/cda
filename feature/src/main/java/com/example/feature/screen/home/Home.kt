package com.example.feature.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.DirectoryWithTasks
import com.example.domain.presentation.HomeStatus
import com.example.domain.presentation.Route
import com.example.feature.component.cards.TaskCard
import com.example.feature.component.cards.input.DirectoryInputCard
import com.example.feature.component.cards.input.TaskInputCard
import com.example.feature.component.dialog.DeleteDirectoryDialog
import com.example.feature.component.dialog.DeleteTaskDialog

@Composable
fun Home(
    homeState: HomeState,
    homeStatus: HomeStatus,
    directories: List<DirectoryWithTasks>,
    tabIndex: Int,
    onNavigate: (Route) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
    onUpdateHomeStatus: (HomeStatus) -> Unit,
) {
    DisposableEffect(Unit) {
        onDispose {
            onUpdateHomeStatus(HomeStatus.DEFAULT)
        }
    }

    when {
        directories.isNotEmpty() ->
            TaskList(
                homeState = homeState,
                directories = directories,
                tabIndex = tabIndex,
                onNavigate = onNavigate,
                onHomeAction = onHomeAction,
            )
        else -> {}
    }

    when (homeStatus) {
        HomeStatus.DEFAULT -> { /*Nothing*/ }
        HomeStatus.CREATING_DIRECTORY -> {
            DirectoryInputCard(
                onCreate = { title ->
                    onHomeAction(
                        HomeAction.InsertDirectory(
                            label = title,
                        ),
                    )
                },
                onUpdateHomeStatus = onUpdateHomeStatus,
            )
        }
        HomeStatus.CREATING_TASK -> {
            TaskInputCard(
                onCreate = { title, description, priority ->
                    onHomeAction(
                        HomeAction.InsertTask(
                            title = title,
                            description = description,
                            priority = priority,
                        ),
                    )
                },
                onUpdateHomeStatus = onUpdateHomeStatus,
            )
        }
        HomeStatus.DELETING_TASK -> {
            DeleteTaskDialog(
                onUpdateHomeStatus = onUpdateHomeStatus,
                currentDirectoryWithTasks = homeState.directories[homeState.selectedDirectoryIndex],
                onHomeAction = onHomeAction,
            )
        }
        HomeStatus.DELETING_DIRECTORY -> {
            DeleteDirectoryDialog(
                onUpdateHomeStatus = onUpdateHomeStatus,
                currentDirectoryWithTasks = homeState.directories[homeState.selectedDirectoryIndex],
                onHomeAction = onHomeAction,
            )
        }
    }
}

@Composable
private fun TaskList(
    homeState: HomeState,
    directories: List<DirectoryWithTasks>,
    tabIndex: Int,
    onNavigate: (Route) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface),
    ) {
        item { DirectoryInformationBar(homeState) }
        if (directories[tabIndex].tasks.isNotEmpty()) {
            items(directories[tabIndex].tasks.size) {
                Box(
                    modifier = Modifier.padding(vertical = 1.dp, horizontal = 8.dp),
                ) {
                    TaskCard(
                        task = directories[tabIndex].tasks[it],
                        onNavigate = onNavigate,
                        onHomeAction = onHomeAction,
                    )
                }
            }
        }
    }
}

@Composable
private fun DirectoryInformationBar(homeState: HomeState) {
    val countOfUnCompletedTasks =
        homeState.directories[homeState.selectedDirectoryIndex]
            .tasks
            .filter { !it.isCompleted }
            .size
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(modifier = Modifier.align(Alignment.CenterStart), text = "未達成: $countOfUnCompletedTasks")
    }
}

@Composable
@Preview
private fun HomePreview() {
    Home(
        directories = listOf(),
        tabIndex = 0,
        homeStatus = HomeStatus.DEFAULT,
        onHomeAction = {},
        onUpdateHomeStatus = {},
        homeState = HomeState(),
        onNavigate = {},
    )
}
