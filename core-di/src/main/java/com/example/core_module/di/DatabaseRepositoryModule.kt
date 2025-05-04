package com.example.core_module.di

import android.content.Context
import androidx.room.Room
import com.example.data.repository.DirectoryRepositoryImpl
import com.example.domain.dao.DirectoryDao
import com.example.domain.db.DirectoryDatabase
import com.example.domain.repository.DirectoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseRepositoryModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): DirectoryDatabase =
        Room
            .databaseBuilder(
                context,
                DirectoryDatabase::class.java,
                "directory_database",
            ).build()

    @Provides
    @Singleton
    fun provideDirectoryDao(database: DirectoryDatabase): DirectoryDao = database.directoryDao()

    @Provides
    @Singleton
    fun provideDirectoryRepository(directoryDao: DirectoryDao): DirectoryRepository =
        DirectoryRepositoryImpl(
            directoryDao = directoryDao,
        )
}
