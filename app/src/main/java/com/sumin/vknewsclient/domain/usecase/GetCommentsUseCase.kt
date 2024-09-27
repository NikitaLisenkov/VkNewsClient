package com.sumin.vknewsclient.domain.usecase

import com.sumin.vknewsclient.domain.model.comment.PostComment
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository

class GetCommentsUseCase(private val repo: NewsFeedRepository) {

    suspend operator fun invoke(feedPost: FeedPostModel): List<PostComment> {
        return repo.getComments(feedPost)
    }
}