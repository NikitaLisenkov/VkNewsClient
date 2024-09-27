package com.sumin.vknewsclient.presentation.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _authState: MutableStateFlow<LoginState> =
        MutableStateFlow(LoginState.Initial)
    val authState: StateFlow<LoginState> = _authState.asStateFlow()


    init {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        val loggedIn = token != null && token.isValid
        Log.d("LoginViewModel", "Token: ${token?.accessToken}")
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