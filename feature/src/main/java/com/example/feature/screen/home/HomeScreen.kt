package com.example.feature.screen.home

import com.example.domain.model.DirectoryWithTasks
import com.example.domain.model.Task
import com.example.domain.presentation.HomeStatus
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object HomeScreen : Screen {
    data class HomeState(
        val directories: List<DirectoryWithTasks> = listOf(),
        val selectedDirectoryIndex: Int = 0,
        val homeStatus: HomeStatus = HomeStatus.DEFAULT,
        val event: (HomeEvent) -> Unit,
    ) : CircuitUiState

    sealed interface HomeEvent : CircuitUiState {
        data object GetAllDirectoryWithTasks : HomeEvent

        data class UpdateTask(
            val task: Task,
        ) : HomeEvent

        data class UpdateHomeStatus(
            val newHomeStatus: HomeStatus,
        ) : HomeEvent

        data class DeleteTask(
            val directoryWithTasks: DirectoryWithTasks,
        ) : HomeEvent

        data class DeleteDirectory(
            val directoryWithTasks: DirectoryWithTasks,
        ) : HomeEvent

        data class SelectDirectory(
            val index: Int,
        ) : HomeEvent

        data class InsertTask(
            val title: String,
            val description: String,
        ) : HomeEvent

        data class InsertDirectory(
            val label: String,
        ) : HomeEvent
    }
}
