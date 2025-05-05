package com.example.core_module.di

import com.example.domain.repository.DirectoryRepository
import com.example.feature.screen.home.HomePresenter
import com.example.feature.screen.tododetail.TodoDetailPresenter
import com.slack.circuit.runtime.presenter.Presenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object FactoryModule {
    @Provides
    @IntoSet
    fun provideHomePresenterFactory(directoryRepository: DirectoryRepository): Presenter.Factory =
        HomePresenter.Factory(directoryRepository)

    @Provides
    @IntoSet
    fun provideTodoDetailPresenterFactory(directoryRepository: DirectoryRepository): Presenter.Factory =
        TodoDetailPresenter.Factory(directoryRepository)
}
