package com.sumin.vknewsclient.domain.model.comment


data class CommentsData(
    val comments: List<PostComment>,
    val totalCount: Int
)

data class PostComment(
    val id: Long,
    val authorName: String,
    val authorAvatarUrl: String,
    val commentText: String,
    val publicationDate: String
)
