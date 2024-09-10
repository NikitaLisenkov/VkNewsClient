package com.sumin.vknewsclient.data.mapper

import com.sumin.vknewsclient.data.network.model.NewsFeedResponseDto
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.model.post.StatisticItem
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapDtoToDomain(newsFeed: NewsFeedResponseDto): List<FeedPostModel> {
        val result = mutableListOf<FeedPostModel>()
        val posts = newsFeed.newsFeedContent.posts
        val groups = newsFeed.newsFeedContent.groups

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: continue
            val feedPost = FeedPostModel(
                id = post.id,
                communityName = group.name,
                publicationDate = post.date.toString(),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticItem.StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticItem.StatisticType.VIEWS, post.views.count),
                    StatisticItem(type = StatisticItem.StatisticType.SHARES, post.reposts.count),
                    StatisticItem(type = StatisticItem.StatisticType.COMMENTS, post.comments.count)
                )
            )
            result.add(feedPost)
        }
        return result
    }
}