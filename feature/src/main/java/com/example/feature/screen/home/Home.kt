package com.example.feature.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.presentation.FloatingActionButtonItem
import com.example.domain.presentation.HomeStatus
import com.example.feature.R
import com.example.feature.component.bars.TabBar
import com.example.feature.component.buttons.multi_fabs.MultiFloatingActionButton
import com.example.feature.component.cards.TaskCard
import com.example.feature.component.cards.input.DirectoryInputCard
import com.example.feature.component.cards.input.TaskInputCard
import com.example.feature.component.dialog.DeleteDirectoryDialog
import com.example.feature.component.dialog.DeleteTaskDialog
import com.slack.circuit.codegen.annotations.CircuitInject
import dagger.hilt.components.SingletonComponent

@CircuitInject(HomeScreen::class, SingletonComponent::class)
@Composable
fun Home(
    state: HomeScreen.HomeState,
    modifier: Modifier,
) {
    HomeUi(
        state = state,
        homeStatus = state.homeStatus,
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            when {
                state.directories.isNotEmpty() -> TaskList(state = state)
                else -> {}
            }
        }
    }
}

@Composable
private fun HomeUi(
    state: HomeScreen.HomeState,
    homeStatus: HomeStatus,
    content: @Composable () -> Unit,
) {
    val items =
        when {
            state.directories.isNotEmpty() -> {
                listOf(
                    FloatingActionButtonItem(
                        icon = painterResource(R.drawable.baseline_note_add_24),
                        label = "create todo",
                        homeStatus = HomeStatus.CREATING_TASK,
                        onClick = { state.event(HomeScreen.HomeEvent.UpdateHomeStatus(it)) },
                    ),
                    FloatingActionButtonItem(
                        icon = painterResource(R.drawable.baseline_delete_24),
                        label = "delete todo",
                        homeStatus = HomeStatus.DELETING_TASK,
                        onClick = { state.event(HomeScreen.HomeEvent.UpdateHomeStatus(it)) },
                    ),
                    FloatingActionButtonItem(
                        icon = painterResource(R.drawable.baseline_create_new_folder_24),
                        label = "create directory",
                        homeStatus = HomeStatus.CREATING_DIRECTORY,
                        onClick = { state.event(HomeScreen.HomeEvent.UpdateHomeStatus(it)) },
                    ),
                    FloatingActionButtonItem(
                        icon = painterResource(R.drawable.baseline_folder_delete_24),
                        label = "delete directory",
                        homeStatus = HomeStatus.DELETING_DIRECTORY,
                        onClick = {
                            state.event(HomeScreen.HomeEvent.UpdateHomeStatus(it))
                        },
                    ),
                )
            }
            else -> {
                listOf(
                    FloatingActionButtonItem(
                        icon = painterResource(R.drawable.baseline_create_new_folder_24),
                        label = "create directory",
                        homeStatus = HomeStatus.CREATING_DIRECTORY,
                        onClick = { state.event(HomeScreen.HomeEvent.UpdateHomeStatus(it)) },
                    ),
                )
            }
        }

    Scaffold(
        topBar = { TabBar(state) },
        floatingActionButton = {
            MultiFloatingActionButton(
                state = state,
                items = items,
            )
        },
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            content()
            when (homeStatus) {
                HomeStatus.DEFAULT -> { /*Nothing*/ }
                HomeStatus.CREATING_DIRECTORY -> {
                    DirectoryInputCard(state)
                }
                HomeStatus.CREATING_TASK -> {
                    TaskInputCard(state)
                }
                HomeStatus.DELETING_TASK -> {
                    DeleteTaskDialog(state)
                }
                HomeStatus.DELETING_DIRECTORY -> {
                    DeleteDirectoryDialog(state)
                }
            }
        }
    }
}

@Composable
private fun TaskList(state: HomeScreen.HomeState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface),
    ) {
        item { DirectoryInformationBar(state) }
        if (state.directories[state.selectedDirectoryIndex].tasks.isNotEmpty()) {
            items(state.directories[state.selectedDirectoryIndex].tasks.size) {
                Box(
                    modifier = Modifier.padding(vertical = 1.dp, horizontal = 8.dp),
                ) {
                    TaskCard(
                        task = state.directories[state.selectedDirectoryIndex].tasks[it],
                        state = state,
                    )
                }
            }
        }
    }
}

@Composable
private fun DirectoryInformationBar(state: HomeScreen.HomeState) {
    val countOfUnCompletedTasks =
        state.directories[state.selectedDirectoryIndex]
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
    Home(state = HomeScreen.HomeState {}, modifier = Modifier)
}
