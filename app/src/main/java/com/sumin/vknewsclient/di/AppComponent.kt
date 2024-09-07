package com.sumin.vknewsclient.di

import com.sumin.vknewsclient.data.post.FeedRepositoryImpl
import com.sumin.vknewsclient.domain.post.FeedRepository

object AppComponent {
    val feedRepository: FeedRepository = FeedRepositoryImpl()
}