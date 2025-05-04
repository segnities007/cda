package com.example.feature.screen.tododetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.domain.model.Task
import com.example.domain.repository.DirectoryRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@CircuitInject(TodoDetailScreen::class, SingletonComponent::class)
class TodoDetailPresenter
    @Inject
    constructor(
        private val directoryRepository: DirectoryRepository,
    ) : Presenter<TodoDetailScreen.TodoDetailState> {
        @Composable
        override fun present(): TodoDetailScreen.TodoDetailState {
            val scope = rememberCoroutineScope()
            var task by remember { mutableStateOf(Task()) }

            return TodoDetailScreen.TodoDetailState(
                task = task,
            ) { event ->
                when (event) {
                    TodoDetailScreen.TodoDetailEvent.BackNavigate -> {
//                        navigator.pop()
                    }
                    is TodoDetailScreen.TodoDetailEvent.GetTaskById -> {
                        scope.launch(Dispatchers.IO) {
                            task = directoryRepository.getTaskById(event.taskId)
                        }
                    }
                }
            }
        }
    }
