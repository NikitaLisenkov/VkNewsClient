package com.sumin.vknewsclient.domain.model.post

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class FeedPostModel(
    val id: Long,
    val communityId: Long,
    val communityName: String,
    val publicationDate: String,
    val communityImageUrl: String,
    val contentText: String,
    val contentImageUrl: String?,
    val statistics: List<StatisticItem>,
    val isLiked: Boolean
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
