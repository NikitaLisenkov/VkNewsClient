package com.sumin.vknewsclient.data.repository

import com.sumin.vknewsclient.data.mapper.NewsFeedMapper
import com.sumin.vknewsclient.data.network.VkApi
import com.sumin.vknewsclient.domain.model.comment.CommentsData
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.model.post.StatisticItem
import com.sumin.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val api: VkApi,
    private val mapper: NewsFeedMapper
) : NewsFeedRepository {

    private val _feedPosts = mutableListOf<FeedPostModel>()
    val feedPosts: List<FeedPostModel>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    /**
     * null может быть в 2 случаях: когда впервые запускаем загрузку списка, и когда рекоммендации для юзера закончились
     */
    override suspend fun loadPosts(): List<FeedPostModel> {
        val startFrom = nextFrom

        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts

        val response =
            if (startFrom == null) {
                api.loadNewsFeed()
            } else {
                api.loadNextNewsFeed(startFrom)
            }
        nextFrom = response.newsFeedContent.nextFrom
        val posts = mapper.mapNewsDtoToDomain(response)
        _feedPosts.addAll(posts)
        return feedPosts
    }


    override suspend fun deleteItem(feedPost: FeedPostModel) {
        api.ignoreItem(
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
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            api.addLike(
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


    override suspend fun getComments(feedPost: FeedPostModel, offset: Int): CommentsData {
        val response = api.getComments(
            ownerId = feedPost.communityId,
            postId = feedPost.id,
            offset = offset,
            count = PAGE_SIZE
        )
        return mapper.mapCommentsDtoToDomain(response)
    }


    companion object {
        private const val PAGE_SIZE: Int = 30
    }
}

