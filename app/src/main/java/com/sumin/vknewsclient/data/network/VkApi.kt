package com.sumin.vknewsclient.data.network

import com.sumin.vknewsclient.data.network.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApi {

    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadNewsFeed(
        @Query("access_token") token: String
    ) : NewsFeedResponseDto
}