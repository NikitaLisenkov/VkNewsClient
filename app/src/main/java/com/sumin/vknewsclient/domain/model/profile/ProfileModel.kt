package com.sumin.vknewsclient.domain.model.profile


data class ProfileModel(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val homeTown: String,
    val status: String,
    val avatarUrl: String,
    val birthdayDate: String
)
