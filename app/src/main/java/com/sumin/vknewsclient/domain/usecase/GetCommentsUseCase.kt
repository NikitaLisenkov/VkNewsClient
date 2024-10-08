package com.sumin.vknewsclient.domain.usecase

import com.sumin.vknewsclient.domain.model.comment.CommentsData
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val repo: NewsFeedRepository) {

    suspend operator fun invoke(feedPost: FeedPostModel, offset: Int): CommentsData {
        return repo.getComments(feedPost, offset)
    }
}