package com.sumin.vknewsclient.data.mapper

import com.sumin.vknewsclient.data.network.model.MyProfileResponseDto
import com.sumin.vknewsclient.domain.model.profile.ProfileModel
import javax.inject.Inject

class ProfileMapper @Inject constructor() {

    fun mapProfileDtoToDomain(dto: MyProfileResponseDto): ProfileModel {
        return ProfileModel(
            id = dto.myProfile.id,
            firstName = dto.myProfile.firstName,
            lastName = dto.myProfile.lastName,
            phone = dto.myProfile.phone ?: "Phone number not specified",
            homeTown = dto.myProfile.homeTown ?: "City not specified",
            status = dto.myProfile.status ?: "Status not specified",
            avatarUrl = dto.myProfile.avatarUrl ?: "",
            birthdayDate = dto.myProfile.birthdayDate ?: "No date specified"
        )
    }
}