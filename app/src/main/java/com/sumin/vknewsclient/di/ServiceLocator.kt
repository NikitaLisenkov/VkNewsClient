package com.sumin.vknewsclient.di

import com.sumin.vknewsclient.data.network.VkApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServiceLocator {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.vk.com/method/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val api: VkApi = retrofit.create()
}