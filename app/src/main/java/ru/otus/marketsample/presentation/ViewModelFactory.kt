package ru.otus.marketsample.presentation

import androidx.lifecycle.ViewModel

//without assist
interface ViewModelFactory {
    fun <VM: ViewModel> create(modelClass: Class<VM>): VM
}