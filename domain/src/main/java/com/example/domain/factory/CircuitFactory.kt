package com.example.domain.factory

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CircuitFactoriesEntryPoint {
    fun uiFactories(): Set<Ui.Factory>

    fun presenterFactories(): Set<Presenter.Factory>
}
