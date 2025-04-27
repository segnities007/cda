package com.example.data.model

import com.example.domain.presentation.NavigationBarItem
import com.example.domain.presentation.Route

data class NavItem(
    override val selectedIcon: Int,
    override val unSelectedIcon: Int,
    override val label: String,
    override val route: Route,
) : NavigationBarItem
