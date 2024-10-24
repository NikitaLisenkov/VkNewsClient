package com.sumin.vknewsclient.data.network.model

import com.google.gson.annotations.SerializedName

data class LikesCountResponseDto(
    @SerializedName("response") val likesCount: LikesCountDto
)
