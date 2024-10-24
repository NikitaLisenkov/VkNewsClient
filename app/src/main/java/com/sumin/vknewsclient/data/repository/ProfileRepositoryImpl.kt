package com.sumin.vknewsclient.data.repository

import android.util.Log
import com.sumin.vknewsclient.data.mapper.ProfileMapper
import com.sumin.vknewsclient.data.network.VkApi
import com.sumin.vknewsclient.domain.model.profile.ProfileModel
import com.sumin.vknewsclient.domain.repository.ProfileRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val storage: VKPreferencesKeyValueStorage,
    private val api: VkApi,
    private val mapper: ProfileMapper
) : ProfileRepository {

    private val token
        get() = VKAccessToken.restore(storage)

    private fun getAccessToken(): String {
        val token = token?.accessToken ?: throw IllegalStateException("Token is null")
        Log.d("qwe", "Token: $token")
        return token
    }

    override suspend fun getProfile(): ProfileModel {
        val response = api.getProfileInfo(getAccessToken())
        return mapper.mapProfileDtoToDomain(response)
    }
}