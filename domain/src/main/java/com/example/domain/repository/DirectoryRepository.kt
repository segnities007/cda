package com.example.domain.repository

import com.example.domain.model.Directory
import com.example.domain.model.DirectoryWithTasks
import com.example.domain.model.Task

interface DirectoryRepository {
    suspend fun insertTask(task: Task)

    suspend fun insertDirectory(directory: Directory)

    suspend fun updateTask(task: Task)

    suspend fun updateDirectory(directory: Directory)

    suspend fun deleteTask(task: Task)

    suspend fun deleteDirectory(directory: Directory)

    suspend fun getAllDirectoryWithTasks(): List<DirectoryWithTasks>

    suspend fun getTaskById(taskId: Int): Task
}
