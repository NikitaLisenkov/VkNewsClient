package com.sumin.vknewsclient.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Если в каком-то месте программы нам понадобится какая-либо из вью моделей, то мы будем получать ее из ViewModelFactory.
 * На момент компиляции в Map-у будут положены все вью модели по их ключам.
 * Внутри фабрики будет вызван метод create, и из коллекции по названию класса мы получим провайдер вью модели,
 * у провайдеры вызовем метод get(), вью модель будет создана, и мы сможем с ней работать.
 */

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val viewModelProviders: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }
}