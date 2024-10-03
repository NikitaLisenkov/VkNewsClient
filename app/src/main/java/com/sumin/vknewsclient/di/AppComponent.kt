package com.sumin.vknewsclient.di

import android.content.Context
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
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

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context,
            @BindsInstance feedPost: FeedPostModel
        ): AppComponent
    }
}