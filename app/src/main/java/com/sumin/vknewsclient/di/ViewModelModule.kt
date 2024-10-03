package com.sumin.vknewsclient.di

import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.presentation.comments.CommentsViewModel
import com.sumin.vknewsclient.presentation.login.LoginViewModel
import com.sumin.vknewsclient.presentation.news.NewsFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindCommentsViewModel(viewModel: CommentsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    @Binds
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}