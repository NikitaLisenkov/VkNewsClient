package com.sumin.vknewsclient.di

import android.content.Context
import com.sumin.vknewsclient.presentation.ViewModelFactory
import com.sumin.vknewsclient.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)

interface AppComponent {
    /**
     * Используется вместо функции inject для предоставления фабрики VM
     */
    fun getViewModelFactory(): ViewModelFactory

    fun getCommentsScreenComponentFactory(): CommentsScreenComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}