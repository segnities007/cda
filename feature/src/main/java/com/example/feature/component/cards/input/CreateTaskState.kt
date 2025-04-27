package com.example.feature.component.cards.input

import com.example.domain.model.Priority

data class CreateTaskState(
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.Medium,
)
