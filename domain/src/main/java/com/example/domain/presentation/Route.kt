package com.example.domain.presentation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    @SerialName("Home")
    data object Home : Route

    @Serializable
    @SerialName("TodoDetail")
    data class TodoDetail(
        val taskId: Int,
    ) : Route
}
