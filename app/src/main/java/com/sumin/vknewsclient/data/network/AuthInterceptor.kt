package com.sumin.vknewsclient.data.network

import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val storage: VKPreferencesKeyValueStorage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = VKAccessToken.restore(storage)?.accessToken
            ?: throw IllegalStateException("Token is null")

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}
