package ru.otus.marketsample.presentation.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
)
@MapKey
annotation class ViewModelFactoryKey(val value: KClass<out ViewModel>)
