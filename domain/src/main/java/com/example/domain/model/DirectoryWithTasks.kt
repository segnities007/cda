package com.example.domain.model

import androidx.room.Embedded
import androidx.room.Relation

data class DirectoryWithTasks(
    @Embedded val directory: Directory = Directory(),
    @Relation(
        parentColumn = "id",
        entityColumn = "directoryId",
    )
    val tasks: List<Task> = listOf(),
)
