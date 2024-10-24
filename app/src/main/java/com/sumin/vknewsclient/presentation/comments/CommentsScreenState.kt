package com.sumin.vknewsclient.presentation.comments

import com.sumin.vknewsclient.domain.post.FeedPostModel
import com.sumin.vknewsclient.domain.comment.PostComment

sealed class CommentsScreenState {
    data object Initial : CommentsScreenState()
    data class Comments(
        val post: FeedPostModel,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}