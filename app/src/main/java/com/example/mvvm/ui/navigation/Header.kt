package com.example.mvvm.ui.navigation

import androidx.compose.runtime.Composable
import com.example.domain.presentation.Route
import com.example.feature.component.bars.TabBar
import com.example.feature.component.bars.TopBar

@Composable
internal fun Header(
    route: Route,
    tabIndex: Int,
    labels: List<String>,
    onTabSelected: (Int) -> Unit,
    onBackNavigate: () -> Unit,
) {
    when (route) {
        Route.Home -> {
            TabBar(
                labels,
                tabIndex = tabIndex,
                onTabSelected = onTabSelected,
            )
        }
        is Route.TodoDetail -> {
            TopBar(
                onBackNavigate = onBackNavigate,
            )
        }
    }
}
