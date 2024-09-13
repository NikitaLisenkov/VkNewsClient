package com.sumin.vknewsclient.data.mapper

import com.sumin.vknewsclient.data.network.model.NewsFeedResponseDto
import com.sumin.vknewsclient.domain.post.FeedPostModel
import com.sumin.vknewsclient.domain.post.StatisticItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
                communityId = post.communityId,
                communityName = group.name,
                publicationDate = mapTimestampToDate(post.date * 1000),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticItem.StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticItem.StatisticType.VIEWS, post.views.count),
                    StatisticItem(type = StatisticItem.StatisticType.SHARES, post.reposts.count),
                    StatisticItem(type = StatisticItem.StatisticType.COMMENTS, post.comments.count)
                ),
                isLiked = post.likes.userLikes > 0
            )
            result.add(feedPost)
        }
        return result
    }


    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp)
        return SimpleDateFormat("dd MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}