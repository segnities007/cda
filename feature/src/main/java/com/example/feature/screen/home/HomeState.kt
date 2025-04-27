package com.example.feature.screen.home

import com.example.domain.model.DirectoryWithTasks

data class HomeState(
    val directories: List<DirectoryWithTasks> = listOf(),
    val selectedDirectoryIndex: Int = 0,
)
