package com.example.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.dao.DirectoryDao
import com.example.domain.model.Directory
import com.example.domain.model.Task

@Database(
    entities = [Directory::class, Task::class],
    version = 1,
    exportSchema = false,
)
abstract class DirectoryDatabase : RoomDatabase() {
    abstract fun directoryDao(): DirectoryDao
}
