package com.sumin.vknewsclient.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sumin.vknewsclient.domain.usecase.CheckAuthUseCase
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAuthenticationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val checkAuthUseCase = CheckAuthUseCase(storage)

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