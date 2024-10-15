package com.sumin.vknewsclient.di.app

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sumin.vknewsclient.data.network.AuthInterceptor
import com.sumin.vknewsclient.data.network.VkApi
import com.sumin.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.sumin.vknewsclient.data.repository.ProfileRepositoryImpl
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import com.sumin.vknewsclient.domain.repository.ProfileRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindNewsRepo(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    @ApplicationScope
    @Binds
    fun bindProfileRepo(impl: ProfileRepositoryImpl): ProfileRepository

    companion object {

        private const val BASE_URL = "https://api.vk.com/method/"

        @ApplicationScope
        @Provides
        fun provideOkHttp(storage: VKPreferencesKeyValueStorage): OkHttpClient {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val authInterceptor = AuthInterceptor(storage)
            return OkHttpClient
                .Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(logging)
                .build()
        }

        @ApplicationScope
        @Provides
        fun provideGson(): Gson = GsonBuilder().create()

        @ApplicationScope
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
        }


        @ApplicationScope
        @Provides
        fun provideApi(retrofit: Retrofit): VkApi {
            return retrofit.create(VkApi::class.java)
        }

        @ApplicationScope
        @Provides
        fun provideStorage(context: Context): VKPreferencesKeyValueStorage {
            return VKPreferencesKeyValueStorage(context)
        }
    }
}
