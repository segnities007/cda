package com.example.domain.presentation

import androidx.compose.ui.graphics.painter.Painter

data class FloatingActionButtonItem(
    val icon: Painter,
    val label: String,
    val homeStatus: HomeStatus,
    val onClick: (HomeStatus) -> Unit,
)
