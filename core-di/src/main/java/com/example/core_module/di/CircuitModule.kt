package com.example.core_module.di

import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.Multibinds

@Module
@InstallIn(SingletonComponent::class)
abstract class CircuitModule {
    @Multibinds
    abstract fun bindPresenterFactories(): Set<@JvmSuppressWildcards Presenter.Factory>

    @Multibinds
    abstract fun bindUiFactories(): Set<@JvmSuppressWildcards Ui.Factory>
}
