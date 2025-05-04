package com.example.feature.screen.tododetail

import com.example.domain.model.Task
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object TodoDetailScreen : Screen {
    data class TodoDetailState(
        val task: Task = Task(),
        val event: (TodoDetailEvent) -> Unit,
    ) : CircuitUiState

    sealed interface TodoDetailEvent : CircuitUiEvent {
        data object BackNavigate : TodoDetailEvent

        data class GetTaskById(
            val taskId: Int,
        ) : TodoDetailEvent
    }
}
