package com.sumin.vknewsclient.data.repository

import com.sumin.vknewsclient.data.mapper.ProfileMapper
import com.sumin.vknewsclient.data.network.VkApi
import com.sumin.vknewsclient.domain.model.profile.ProfileModel
import com.sumin.vknewsclient.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: VkApi,
    private val mapper: ProfileMapper
) : ProfileRepository {

    override suspend fun getProfile(): ProfileModel {
        val response = api.getProfileInfo()
        return mapper.mapProfileDtoToDomain(response)
    }
}