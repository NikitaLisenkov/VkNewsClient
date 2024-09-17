package com.sumin.vknewsclient.domain.repository

import com.sumin.vknewsclient.domain.post.FeedPostModel

interface NewsFeedRepository {

    suspend fun loadPosts(): List<FeedPostModel>

    suspend fun changeLike(feedPost: FeedPostModel)

    suspend fun deleteItem(feedPost: FeedPostModel)
}