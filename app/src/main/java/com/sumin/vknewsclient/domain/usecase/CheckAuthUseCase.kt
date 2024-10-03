package com.sumin.vknewsclient.domain.usecase

import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(private val storage: VKPreferencesKeyValueStorage) {

    operator fun invoke(): Boolean {
        val token = VKAccessToken.restore(storage)
        val tokenIsValid = token != null && token.isValid
        return tokenIsValid
    }
}