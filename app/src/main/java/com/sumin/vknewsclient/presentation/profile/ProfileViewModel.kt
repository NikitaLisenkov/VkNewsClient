package com.sumin.vknewsclient.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumin.vknewsclient.domain.usecase.GetProfileUseCase
import com.sumin.vknewsclient.utils.runSuspendCatching
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    private val _profileState: MutableStateFlow<ProfileScreenState> =
        MutableStateFlow(ProfileScreenState.Loading)
    val profileState: StateFlow<ProfileScreenState> = _profileState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            delay(1000)
            runSuspendCatching(
                block = { getProfileUseCase.invoke() },
                onSuccess = { profile ->
                    _profileState.value = ProfileScreenState.Content(profile)
                },
                onError = {
                    _profileState.value = ProfileScreenState.Error
                }
            )
        }
    }
}