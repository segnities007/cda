package com.example.feature.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.domain.model.Directory
import com.example.domain.model.DirectoryWithTasks
import com.example.domain.model.Task
import com.example.domain.presentation.HomeStatus
import com.example.domain.repository.DirectoryRepository
import com.example.feature.screen.home.HomeScreen.HomeEvent
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@CircuitInject(HomeScreen::class, SingletonComponent::class)
class HomePresenter
    @Inject
    constructor(
        private val directoryRepository: DirectoryRepository,
    ) : Presenter<HomeScreen.HomeState> {
        @Composable
        override fun present(): HomeScreen.HomeState {
            val scope = rememberCoroutineScope()
            var directories by remember { mutableStateOf<List<DirectoryWithTasks>>(listOf()) }
            var selectedDirectoryIndex by remember { mutableIntStateOf(0) }
            var homeStatus by remember { mutableStateOf(HomeStatus.DEFAULT) }

            return HomeScreen.HomeState(
                directories = directories,
                selectedDirectoryIndex = selectedDirectoryIndex,
            ) { event ->
                when (event) {
                    is HomeEvent.SelectDirectory -> {
                        selectedDirectoryIndex = event.index
                    }
                    is HomeEvent.InsertTask -> {
                        scope.launch(Dispatchers.IO) {
                            val newTask =
                                Task(
                                    title = event.title,
                                    description = event.description,
                                    directoryId = directories[selectedDirectoryIndex].directory.id,
                                )
                            directoryRepository.insertTask(newTask)
                            directories = directoryRepository.getAllDirectoryWithTasks()
                        }
                    }
                    is HomeEvent.InsertDirectory -> {
                        scope.launch(Dispatchers.IO) {
                            val newDirectory = Directory(label = event.label)
                            directoryRepository.insertDirectory(newDirectory)
                            directories = directoryRepository.getAllDirectoryWithTasks()
                        }
                    }
                    HomeEvent.GetAllDirectoryWithTasks -> {
                        scope.launch(Dispatchers.IO) {
                            directories = directoryRepository.getAllDirectoryWithTasks()
                        }
                    }

                    is HomeEvent.UpdateTask -> {
                        scope.launch(Dispatchers.IO) {
                            val updatedTask = event.task
                            directoryRepository.updateTask(updatedTask)
                            directories = directoryRepository.getAllDirectoryWithTasks()
                        }
                    }
                    is HomeEvent.DeleteTask -> {
                        scope.launch(Dispatchers.IO) {
                            for (task in event.directoryWithTasks.tasks) {
                                if (task.isCompleted == true) {
                                    directoryRepository.deleteTask(task)
                                }
                            }
                            directoryRepository.getAllDirectoryWithTasks()
                            directories = directoryRepository.getAllDirectoryWithTasks()
                        }
                    }
                    is HomeEvent.DeleteDirectory -> {
                        scope.launch(Dispatchers.IO) {
                            directoryRepository.deleteDirectory(event.directoryWithTasks.directory)
                            directories =
                                directories.filter {
                                    it.directory.id != event.directoryWithTasks.directory.id
                                }
                        }
                    }

                    is HomeEvent.UpdateHomeStatus -> {
                        homeStatus = event.newHomeStatus
                    }
                }
            }
        }
    }
