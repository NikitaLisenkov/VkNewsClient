package com.sumin.vknewsclient.presentation

import com.sumin.vknewsclient.domain.model.FeedPostModel

sealed class NewsFeedScreenState {
    data object Initial : NewsFeedScreenState()
    data class Posts(val posts: List<FeedPostModel>) : NewsFeedScreenState()
}