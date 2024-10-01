package com.sumin.vknewsclient.presentation.comments

import com.sumin.vknewsclient.domain.model.comment.PostComment

data class CommentsScreenState(
    val comments: List<PostComment> = emptyList(),
    val isLoading: Boolean = false
)