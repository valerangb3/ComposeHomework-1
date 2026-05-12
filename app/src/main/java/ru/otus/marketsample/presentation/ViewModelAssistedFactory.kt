package ru.otus.marketsample.presentation

import androidx.lifecycle.ViewModel

interface ViewModelAssistedFactory<T: ViewModel> {
    fun create(): T
}