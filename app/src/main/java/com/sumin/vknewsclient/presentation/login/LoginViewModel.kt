package com.sumin.vknewsclient.presentation.login

import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.usecase.CheckAuthUseCase
import com.vk.api.sdk.auth.VKAuthenticationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    checkAuthUseCase: CheckAuthUseCase
) : ViewModel() {


    private val _authState: MutableStateFlow<LoginState> =
        MutableStateFlow(LoginState.Initial)
    val authState: StateFlow<LoginState> = _authState.asStateFlow()

    init {
        val loggedIn = checkAuthUseCase()
        _authState.value = if (loggedIn) {
            LoginState.Authorized
        } else {
            LoginState.NotAuthorized
        }
    }

    fun authResult(result: VKAuthenticationResult) {
        if (result is VKAuthenticationResult.Success) {
            _authState.value = LoginState.Authorized
        } else {
            _authState.value = LoginState.NotAuthorized
        }
    }
}