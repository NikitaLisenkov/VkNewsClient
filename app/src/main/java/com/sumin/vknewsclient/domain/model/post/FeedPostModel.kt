package com.sumin.vknewsclient.domain.model.post

import com.sumin.vknewsclient.R

data class FeedPostModel(
    val id: Int = 0,
    val communityName: String = "/dev/null",
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem Ipsum - это текст-рыба, часто используемый в печати и вэб-дизайне.",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(type = StatisticItem.StatisticType.VIEWS, count = 966),
        StatisticItem(type = StatisticItem.StatisticType.SHARES, count = 7),
        StatisticItem(type = StatisticItem.StatisticType.COMMENTS, count = 8),
        StatisticItem(type = StatisticItem.StatisticType.LIKES, count = 27)
    )
)
