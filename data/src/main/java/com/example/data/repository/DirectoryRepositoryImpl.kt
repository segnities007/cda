package com.example.data.repository

import com.example.domain.dao.DirectoryDao
import com.example.domain.model.Directory
import com.example.domain.model.DirectoryWithTasks
import com.example.domain.model.Task
import com.example.domain.repository.DirectoryRepository
import javax.inject.Inject

class DirectoryRepositoryImpl
    @Inject
    constructor(
        private val directoryDao: DirectoryDao,
    ) : DirectoryRepository {
        override suspend fun insertTask(task: Task) {
            directoryDao.insertTask(task)
        }

        override suspend fun insertDirectory(directory: Directory) {
            directoryDao.insertDirectory(directory)
        }

        override suspend fun updateTask(task: Task) {
            directoryDao.updateTask(task)
        }

        override suspend fun updateDirectory(directory: Directory) {
            directoryDao.updateDirectory(directory)
        }

        override suspend fun deleteTask(task: Task) {
            directoryDao.deleteTask(task)
        }

        override suspend fun deleteDirectory(directory: Directory) {
            directoryDao.deleteDirectory(directory)
        }

        override suspend fun getAllDirectoryWithTasks(): List<DirectoryWithTasks> = directoryDao.getAllDirectoryWithTasks()
    }
