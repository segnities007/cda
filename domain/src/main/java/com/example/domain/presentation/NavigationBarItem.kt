package com.example.domain.presentation

interface NavigationBarItem {
    val selectedIcon: Int
    val unSelectedIcon: Int
    val label: String
    val route: Route
}
