package com.sumin.vknewsclient.domain.repository

import com.sumin.vknewsclient.domain.model.comment.PostComment
import com.sumin.vknewsclient.domain.model.post.FeedPostModel

interface NewsFeedRepository {

    suspend fun loadPosts(): List<FeedPostModel>

    suspend fun changeLike(feedPost: FeedPostModel)

    suspend fun deleteItem(feedPost: FeedPostModel)

    suspend fun getComments(feedPost: FeedPostModel): List<PostComment>
}