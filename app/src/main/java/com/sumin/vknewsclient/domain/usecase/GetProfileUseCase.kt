package com.sumin.vknewsclient.domain.usecase

import com.sumin.vknewsclient.domain.model.profile.ProfileModel
import com.sumin.vknewsclient.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repo: ProfileRepository) {

    suspend operator fun invoke(): ProfileModel {
        return repo.getProfile()
    }
}