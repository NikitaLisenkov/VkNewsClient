package com.sumin.vknewsclient.data.post

import com.sumin.vknewsclient.domain.post.FeedPostModel
import com.sumin.vknewsclient.domain.post.FeedRepository

class FeedRepositoryImpl : FeedRepository {
    override var cachedFeed: FeedPostModel? = null
}