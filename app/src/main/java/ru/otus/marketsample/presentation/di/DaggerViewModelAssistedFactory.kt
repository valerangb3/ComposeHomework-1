package ru.otus.marketsample.presentation.di

import androidx.lifecycle.ViewModel
import ru.otus.marketsample.presentation.ViewModelFactory
import javax.inject.Provider

class DaggerViewModelAssistedFactory(
    private val factoryMap: Map<Class<out ViewModel>, Provider<ViewModelFactory>>
) : ViewModelFactory {
    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel>  create(modelClass: Class<VM>): VM {
        val creator = factoryMap[modelClass] ?: factoryMap.asIterable().firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        return try {
            creator.get() as VM
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}