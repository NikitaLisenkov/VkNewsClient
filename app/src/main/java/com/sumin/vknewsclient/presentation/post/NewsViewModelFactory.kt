package com.sumin.vknewsclient.presentation.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sumin.vknewsclient.domain.post.FeedRepository

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(
    private val feedRepository: FeedRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsFeedViewModel(repository = feedRepository) as T
    }
}