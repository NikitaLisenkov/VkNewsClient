package com.sumin.vknewsclient.data.mapper

import com.sumin.vknewsclient.data.network.model.CommentsResponseDto
import com.sumin.vknewsclient.data.network.model.NewsFeedResponseDto
import com.sumin.vknewsclient.domain.model.comment.CommentsData
import com.sumin.vknewsclient.domain.model.comment.PostComment
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.model.post.StatisticItem
import com.sumin.vknewsclient.utils.mapTimestampToDate
import javax.inject.Inject
import kotlin.math.absoluteValue

class NewsFeedMapper @Inject constructor() {

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


    fun mapCommentsDtoToDomain(response: CommentsResponseDto): CommentsData {
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
        return CommentsData(
            comments = result,
            totalCount = response.content.totalCount
        )
    }
}