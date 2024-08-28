package com.sumin.vknewsclient.domain.model.comment

import com.sumin.vknewsclient.R

data class PostComment(
    val id: Int,
    val authorName: String = "Author",
    val authorAvatarId: Int = R.drawable.post_comunity_thumbnail,
    val commentText: String = "Text text text",
    val publicationDate: String = "15:00"
)
