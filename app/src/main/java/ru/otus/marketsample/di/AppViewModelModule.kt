package ru.otus.marketsample.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.otus.marketsample.presentation.di.ViewModelFactoryKey
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.ProductListViewModelFactory

@Module
interface AppViewModelModule {
    @Binds
    @[IntoMap ViewModelFactoryKey(ProductListViewModel::class)]
    fun bindsProductListViewModelFactory(factory: ProductListViewModelFactory): ViewModelProvider.Factory
}