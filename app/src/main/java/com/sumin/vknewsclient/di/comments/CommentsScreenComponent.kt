package com.sumin.vknewsclient.di.comments

import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [CommentsViewModelModule::class])
interface CommentsScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        /**
         * CommentsScreenComponent должен иметь возможность получить все зависимости из AppComponent
         * и добавить какие-то свои зависимости.
         * Subcomponent может создать только родительский AppComponent.
         * Для его создания был добавлен метод getCommentsScreenComponentFactory().
         */
        fun create(@BindsInstance feedPost: FeedPostModel): CommentsScreenComponent
    }
}