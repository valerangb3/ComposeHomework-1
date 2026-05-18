package ru.otus.marketsample.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory

@Composable
fun Inject(
    viewModelFactory: ViewModelProvider.Factory,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalViewModelFactory provides viewModelFactory,
        content = content
    )
}

@Composable
inline fun <reified VM: ViewModel> daggerViewModel(): VM {
    val factory = getViewModelFactory()
    return viewModel(factory = factory)
}

@Composable
@PublishedApi
internal fun getViewModelFactory(): ViewModelProvider.Factory {
    return checkNotNull(LocalViewModelFactory.current) {
        "No ViewModelFactory was provided via LocalViewModelFactory"
    }
}

object LocalViewModelFactory {
    private val LocalViewModelFactory =
        compositionLocalOf<ViewModelProvider.Factory?> { null }

    val current: ViewModelProvider.Factory?
        @Composable
        get() = LocalViewModelFactory.current

    infix fun provides(viewModelFactory: ViewModelProvider.Factory):
            ProvidedValue<ViewModelProvider.Factory?> {
        return LocalViewModelFactory.provides(viewModelFactory)
    }
}