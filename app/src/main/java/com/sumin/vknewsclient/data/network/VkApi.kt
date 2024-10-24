package com.sumin.vknewsclient.data.network

import com.sumin.vknewsclient.data.network.model.LikesCountResponseDto
import com.sumin.vknewsclient.data.network.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApi {

    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadNewsFeed(
        @Query("access_token") token: String
    ): NewsFeedResponseDto

    @GET("likes.add?v=5.199&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto


    @GET("likes.delete?v=5.199&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto
}