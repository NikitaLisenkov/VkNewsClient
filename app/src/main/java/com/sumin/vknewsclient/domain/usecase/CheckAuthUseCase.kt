package com.sumin.vknewsclient.domain.usecase

import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class CheckAuthUseCase(private val storage: VKPreferencesKeyValueStorage) {

    operator fun invoke(): Boolean {
        val token = VKAccessToken.restore(storage)
        val tokenIsValid = token != null && token.isValid
        return tokenIsValid
    }
}