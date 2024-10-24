package com.sumin.vknewsclient.domain.usecase

import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository

class GetRecommendationsUseCase(private val repo: NewsFeedRepository) {

    suspend operator fun invoke(): List<FeedPostModel> {
        return repo.loadPosts()
    }
}