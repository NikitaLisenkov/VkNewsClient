package com.sumin.vknewsclient.presentation.login

sealed class LoginState {
    data object Authorized : LoginState()
    data object NotAuthorized : LoginState()
    data object Initial : LoginState()
}