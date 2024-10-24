package com.sumin.vknewsclient.domain.repository

import com.sumin.vknewsclient.domain.model.profile.ProfileModel

interface ProfileRepository {

    suspend fun getProfile(): ProfileModel
}