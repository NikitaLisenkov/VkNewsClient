package com.sumin.vknewsclient.domain.post

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.sumin.vknewsclient.R
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable {

    companion object {
        val navigationType: NavType<FeedPostModel> = object : NavType<FeedPostModel>(false) {
            override fun get(bundle: Bundle, key: String): FeedPostModel? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): FeedPostModel {
                return Gson().fromJson(value, FeedPostModel::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: FeedPostModel) {
                return bundle.putParcelable(key, value)
            }
        }
    }
}
