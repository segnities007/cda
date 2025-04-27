package com.example.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.domain.model.Directory
import com.example.domain.model.DirectoryWithTasks
import com.example.domain.model.Task

@Dao
interface DirectoryDao {
    @Insert
    suspend fun insertTask(task: Task)

    @Insert
    suspend fun insertDirectory(directory: Directory)

    @Delete
    suspend fun deleteTask(task: Task)

    @Delete
    suspend fun deleteDirectory(directory: Directory)

    @Update
    suspend fun updateTask(task: Task)

    @Update
    suspend fun updateDirectory(directory: Directory)

    @Query("SELECT * FROM directories")
    suspend fun getAllDirectoryWithTasks(): List<DirectoryWithTasks>
}
