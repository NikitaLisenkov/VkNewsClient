package com.sumin.vknewsclient.presentation.profile

import com.sumin.vknewsclient.domain.model.profile.ProfileModel

sealed class ProfileScreenState {
    data object Initial: ProfileScreenState()
    data object Loading : ProfileScreenState()
    data class Content(val profile: ProfileModel) : ProfileScreenState()
    data object Error : ProfileScreenState()
}
