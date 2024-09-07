package com.sumin.vknewsclient.presentation.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sumin.vknewsclient.domain.post.FeedRepository

@Suppress("UNCHECKED_CAST")
class CommentsViewModelFactory(
    private val feedId: Int,
    private val repository: FeedRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(
            feedId = feedId,
            repository = repository
        ) as T
    }
}