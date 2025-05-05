package com.example.feature.screen.tododetail

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.domain.model.Task
import com.example.domain.repository.DirectoryRepository
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoDetailPresenter(
    private val taskId: Int,
    private val navigator: Navigator,
    private val directoryRepository: DirectoryRepository,
) : Presenter<TodoDetailScreen.TodoDetailState> {
    @Composable
    override fun present(): TodoDetailScreen.TodoDetailState {
        val scope = rememberCoroutineScope()
        var task by remember { mutableStateOf(Task()) }

        LaunchedEffect(Unit) {
            task = directoryRepository.getTaskById(taskId)
        }

        return TodoDetailScreen.TodoDetailState(
            task = task,
        ) { event ->
            when (event) {
                TodoDetailScreen.TodoDetailEvent.BackNavigate -> {
                    navigator.pop()
                }
                is TodoDetailScreen.TodoDetailEvent.GetTaskById -> {
                    scope.launch(Dispatchers.IO) {
                        task = directoryRepository.getTaskById(event.taskId)
                    }
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
                    is TodoDetailScreen -> return TodoDetailPresenter(
                        directoryRepository = directoryRepository,
                        navigator = navigator,
                        taskId = screen.taskId,
                    )
                    else -> {
                        Log.d("testtodo", "null")
                        return null
                    }
                }
            }
        }
}
