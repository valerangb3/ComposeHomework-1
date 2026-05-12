package ru.otus.marketsample.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.BindsInstance
import dagger.Component
import ru.otus.marketsample.details.feature.di.DetailsComponentDependencies
import ru.otus.marketsample.products.feature.di.ProductListComponentDependencies
import ru.otus.marketsample.promo.feature.di.PromoComponentDependencies
import ru.otus.common.di.Dependencies
import ru.otus.marketsample.presentation.ViewModelFactory
import ru.otus.marketsample.products.feature.ProductListViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DataModule::class,
        AppViewModelModule::class
    ]
)
interface AppComponent:
    Dependencies,
    DetailsComponentDependencies,
    PromoComponentDependencies,
    ProductListComponentDependencies
{
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    //fun getViewModelFactory(): ViewModelProvider.Factory
}