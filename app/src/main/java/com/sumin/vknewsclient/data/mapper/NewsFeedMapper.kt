package com.sumin.vknewsclient.data.mapper

import com.sumin.vknewsclient.data.network.model.CommentsResponseDto
import com.sumin.vknewsclient.data.network.model.NewsFeedResponseDto
import com.sumin.vknewsclient.domain.comment.PostComment
import com.sumin.vknewsclient.domain.post.FeedPostModel
import com.sumin.vknewsclient.domain.post.StatisticItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapNewsDtoToDomain(newsFeed: NewsFeedResponseDto): List<FeedPostModel> {
        val result = mutableListOf<FeedPostModel>()
        val posts = newsFeed.newsFeedContent.posts
        val groups = newsFeed.newsFeedContent.groups

        posts.forEach { post ->
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: return@forEach
            val feedPost = FeedPostModel(
                id = post.id,
                communityId = post.communityId,
                communityName = group.name,
                publicationDate = mapTimestampToDate(post.date),
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


    fun mapCommentsDtoToDomain(response: CommentsResponseDto): List<PostComment> {
        val result = mutableListOf<PostComment>()
        val comments = response.content.comments
        val profiles = response.content.profiles

        comments.forEach { comment ->
            val author = profiles.firstOrNull { it.id == comment.authorId } ?: return@forEach
            val postComment = PostComment(
                id = comment.id,
                authorName = "${author.firstName} ${author.lastName}",
                authorAvatarUrl = author.avatarUrl,
                commentText = comment.text,
                publicationDate = mapTimestampToDate(comment.date)
            )
            result.add(postComment)
        }
        return result
    }


    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return SimpleDateFormat("dd MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}