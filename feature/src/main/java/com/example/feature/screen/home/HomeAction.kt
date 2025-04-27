package com.example.feature.screen.home

import com.example.domain.model.DirectoryWithTasks
import com.example.domain.model.Priority
import com.example.domain.model.Task

sealed interface HomeAction {
    data object GetAllDirectoryWithTasks : HomeAction

    data class UpdateTask(
        val task: Task,
    ) : HomeAction

    data class DeleteTask(
        val directoryWithTasks: DirectoryWithTasks,
    ) : HomeAction

    data class DeleteDirectory(
        val directoryWithTasks: DirectoryWithTasks,
    ) : HomeAction

    data class SelectDirectory(
        val index: Int,
    ) : HomeAction

    data class InsertTask(
        val title: String,
        val description: String,
        val priority: Priority,
    ) : HomeAction

    data class InsertDirectory(
        val label: String,
    ) : HomeAction
}
