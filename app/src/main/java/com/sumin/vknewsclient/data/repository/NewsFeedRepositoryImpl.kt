package com.sumin.vknewsclient.data.repository

import android.app.Application
import com.sumin.vknewsclient.data.mapper.NewsFeedMapper
import com.sumin.vknewsclient.di.ServiceLocator
import com.sumin.vknewsclient.domain.comment.PostComment
import com.sumin.vknewsclient.domain.post.FeedPostModel
import com.sumin.vknewsclient.domain.post.StatisticItem
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepositoryImpl(application: Application) : NewsFeedRepository {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val api = ServiceLocator.api
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPostModel>()
    val feedPosts: List<FeedPostModel>
        get() = _feedPosts.toList()

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    private var nextFrom: String? = null

    /**
     * null может быть в 2 случаях: когда впервые запускаем загрузку списка, и когда рекоммендации для юзера закончились
     */
    override suspend fun loadPosts(): List<FeedPostModel> {
        val startFrom = nextFrom

        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts

        val response =
            if (startFrom == null) api.loadNewsFeed(getAccessToken()) else api.loadNextNewsFeed(
                getAccessToken(),
                startFrom
            )
        nextFrom = response.newsFeedContent.nextFrom
        val posts = mapper.mapNewsDtoToDomain(response)
        _feedPosts.addAll(posts)
        return feedPosts
    }


    override suspend fun deleteItem(feedPost: FeedPostModel) {
        api.ignoreItem(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
    }


    /**Для того, чтобы при лайке каждый раз не запрашивать все данные с сервера, мы сохраним их локально - в репозитории.
     * При успешном добавлении лайка мы получим объект response с актуальным кол-вом лайков
     * Далее мы получаем это кол-во лайков
     * Создаем новую кол-ию с элементами статистки, для этого мы берем старую кол-ию со всеми эл-тами статистики и создаем изменяемую копию
     * Удаляем оттуда эл-нт, который хранит кол-во лайкови вставляем новый, с обновленным значением
     * Далее создаем новый объект Post с обновленными пар-ми
     * Высчитываем индекс старого эл-та
     * По этому индексу кладем новый объект Post
     * После добавления лайка объект будет обновлен в коллекции
     * Теперь мы можем запросить этот объект из viewModel
     */
    override suspend fun changeLike(feedPost: FeedPostModel) {
        val response = if (feedPost.isLiked) {
            api.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            api.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }
        val newLikesCount = response.likesCount.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { item ->
                item.type == StatisticItem.StatisticType.LIKES
            }
            add(StatisticItem(type = StatisticItem.StatisticType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }


    override suspend fun getComments(feedPost: FeedPostModel): List<PostComment> {
        val comments = api.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        return mapper.mapCommentsDtoToDomain(comments)
    }
}

