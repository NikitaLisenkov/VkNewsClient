package com.sumin.vknewsclient.data.network.model

import com.google.gson.annotations.SerializedName

data class MyProfileResponseDto(
    @SerializedName("response") val myProfile: MyProfileContentDto
)

data class MyProfileContentDto(
    @SerializedName("id") val id: Long,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("phone") val phone: String?,
    @SerializedName("home_town") val homeTown: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("photo_200") val avatarUrl: String?,
    @SerializedName("bdate") val birthdayDate: String?
)



