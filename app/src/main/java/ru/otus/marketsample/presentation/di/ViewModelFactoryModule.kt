package ru.otus.marketsample.presentation.di

import dagger.Binds
import dagger.Module
import ru.otus.marketsample.presentation.ViewModelFactory

@Module
interface ViewModelFactoryModule {
    @Binds
    fun bindDaggerViewModelFactory(factory: DaggerViewModelAssistedFactory): ViewModelFactory
}