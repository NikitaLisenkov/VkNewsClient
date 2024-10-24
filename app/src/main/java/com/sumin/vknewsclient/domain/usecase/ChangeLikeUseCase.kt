package com.sumin.vknewsclient.domain.usecase

import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository

class ChangeLikeUseCase(private val repo: NewsFeedRepository) {

    suspend operator fun invoke(feedPost: FeedPostModel) {
        repo.changeLike(feedPost)
    }
}