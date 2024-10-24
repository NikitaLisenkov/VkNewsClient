package com.sumin.vknewsclient.data.network

import com.sumin.vknewsclient.data.network.model.CommentsResponseDto
import com.sumin.vknewsclient.data.network.model.LikesCountResponseDto
import com.sumin.vknewsclient.data.network.model.MyProfileResponseDto
import com.sumin.vknewsclient.data.network.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApi {

    @GET("newsfeed.getRecommended?v=$VERSION")
    suspend fun loadNewsFeed(): NewsFeedResponseDto

    @GET("newsfeed.getRecommended?v=$VERSION")
    suspend fun loadNextNewsFeed(
        @Query("start_from") startFrom: String
    ): NewsFeedResponseDto

    @GET("wall.getComments?v=$VERSION&extended=1&fields=photo_100")
    suspend fun getComments(
        @Query("owner_id") ownerId: Long,
        @Query("post_id") postId: Long,
        @Query("offset") offset: Int,
        @Query("count") count: Int,
    ): CommentsResponseDto

    @GET("newsfeed.ignoreItem?v=$VERSION&type=wall")
    suspend fun ignoreItem(
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    )

    @GET("likes.add?v=$VERSION&type=post")
    suspend fun addLike(
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("likes.delete?v=$VERSION&type=post")
    suspend fun deleteLike(
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("account.getProfileInfo?v=$VERSION")
    suspend fun getProfileInfo(): MyProfileResponseDto


    companion object {
        private const val VERSION = "5.199"
    }
}