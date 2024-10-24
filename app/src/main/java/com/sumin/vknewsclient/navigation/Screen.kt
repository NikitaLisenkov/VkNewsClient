package com.sumin.vknewsclient.navigation

import android.net.Uri
import com.google.gson.Gson
import com.sumin.vknewsclient.domain.model.post.FeedPostModel

sealed class Screen(val route: String) {

    data object Favourite : Screen(ROUTE_FAVOURITE)
    data object Profile : Screen(ROUTE_PROFILE)
    data object Home : Screen(ROUTE_HOME)
    data object NewsFeed : Screen(ROUTE_NEWS_FEED)
    data object Comments : Screen(ROUTE_COMMENTS) {
        private const val ROUTE_FOR_ARGS = "comments"
        fun getRouteWithArgs(feedPost: FeedPostModel): String {
            val feedPostJson = Gson().toJson(feedPost)
            return "$ROUTE_FOR_ARGS/${feedPostJson.encode()}"
        }
    }


    fun String.encode(): String {
        return Uri.encode(this)
    }


    companion object {
        const val KEY_FEED_POST = "feed_post"

        const val ROUTE_HOME = "home"
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST}"
        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
    }
}