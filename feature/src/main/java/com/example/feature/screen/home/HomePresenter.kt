package com.example.feature.screen.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.feature.screen.tododetail.TodoDetailScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePresenter(
    private val navigator: Navigator,
    private val directoryRepository: DirectoryRepository,
) : Presenter<HomeScreen.HomeState> {
    @Composable
    override fun present(): HomeScreen.HomeState {
        val scope = rememberCoroutineScope()
        var directories by remember { mutableStateOf<List<DirectoryWithTasks>>(listOf()) }
        var selectedDirectoryIndex by remember { mutableIntStateOf(0) }
        var homeStatus by remember { mutableStateOf(HomeStatus.DEFAULT) }
        LaunchedEffect(Unit) {
            directories = directoryRepository.getAllDirectoryWithTasks()
        }

        return HomeScreen.HomeState(
            directories = directories,
            selectedDirectoryIndex = selectedDirectoryIndex,
            homeStatus = homeStatus,
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
                        if (directories.last() == event.directoryWithTasks && selectedDirectoryIndex - 1 >= 0) {
                            selectedDirectoryIndex--
                        }
                        directories =
                            directories.filter {
                                it.directory.id != event.directoryWithTasks.directory.id
                            }
                    }
                }

                is HomeEvent.UpdateHomeStatus -> {
                    homeStatus = event.newHomeStatus
                }

                is HomeEvent.NavigateToTodoDetail -> {
                    navigator.goTo(TodoDetailScreen(event.taskId))
                }
            }
        }
    }

    class Factory
        @Inject
        constructor(
            private val directoryRepository: DirectoryRepository,
        ) : Presenter.Factory {
            override fun create(
                screen: Screen,
                navigator: Navigator,
                circuitContext: CircuitContext,
            ): Presenter<*>? {
                return when (screen) {
                    is HomeScreen -> return HomePresenter(directoryRepository = directoryRepository, navigator = navigator)
                    else -> {
                        Log.d("test", "null")
                        return null
                    }
                }
            }
        }
}
