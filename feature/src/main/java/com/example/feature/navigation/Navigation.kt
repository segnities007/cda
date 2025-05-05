package com.example.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.domain.factory.CircuitFactoriesEntryPoint
import com.example.feature.screen.home.HomeScreen
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import dagger.hilt.android.EntryPointAccessors

@Composable
fun Navigation() {
    val context = LocalContext.current.applicationContext
    val entryPoint =
        EntryPointAccessors.fromApplication(
            context,
            CircuitFactoriesEntryPoint::class.java,
        )
    val uiFactories = entryPoint.uiFactories()
    val presenterFactories = entryPoint.presenterFactories()

    val circuit =
        Circuit
            .Builder()
            .addPresenterFactories(presenterFactories)
            .addUiFactories(uiFactories)
            .build()

    val backStack = rememberSaveableBackStack(root = HomeScreen)
    val navigator = rememberCircuitNavigator(backStack)

    CircuitCompositionLocals(circuit) {
        NavigableCircuitContent(circuit = circuit, navigator = navigator, backStack = backStack)
    }
}
