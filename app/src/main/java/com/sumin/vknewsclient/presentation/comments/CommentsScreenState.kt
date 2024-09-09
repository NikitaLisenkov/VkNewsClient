package com.sumin.vknewsclient.presentation.comments

import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.model.comment.PostComment

sealed class CommentsScreenState {
    data object Initial : CommentsScreenState()
    data class Comments(
        val post: FeedPostModel,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}