package com.sumin.vknewsclient.presentation.post

import com.sumin.vknewsclient.domain.model.post.FeedPostModel

sealed class NewsFeedScreenState {
    data object Initial : NewsFeedScreenState()
    data class Posts(val posts: List<FeedPostModel>) : NewsFeedScreenState()
}