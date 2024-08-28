package com.sumin.vknewsclient.presentation.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sumin.vknewsclient.domain.model.post.FeedPostModel

class CommentsViewModelFactory(
    private val feedPost: FeedPostModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost = feedPost) as T
    }
}