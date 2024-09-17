package com.sumin.vknewsclient.data.network

import com.sumin.vknewsclient.data.network.model.LikesCountResponseDto
import com.sumin.vknewsclient.data.network.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApi {

    @GET("newsfeed.getRecommended?v=$VERSION")
    suspend fun loadNewsFeed(
        @Query("access_token") token: String
    ): NewsFeedResponseDto

    @GET("newsfeed.getRecommended?v=$VERSION")
    suspend fun loadNextNewsFeed(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String
    ): NewsFeedResponseDto

    @GET("newsfeed.ignoreItem?v=$VERSION&type=wall")
    suspend fun ignoreItem(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    )

    @GET("likes.add?v=$VERSION&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto


    @GET("likes.delete?v=$VERSION&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto


    companion object {
        private const val VERSION = "5.199"
    }
}