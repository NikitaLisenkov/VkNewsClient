package com.sumin.vknewsclient.data.network.model

import com.google.gson.annotations.SerializedName

data class CommentsContentDto(
    @SerializedName("count") val totalCount: Int,
    @SerializedName("items") val comments: List<CommentDto>,
    @SerializedName("profiles") val profiles: List<ProfileDto>
)
