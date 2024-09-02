package com.sumin.vknewsclient.navigation

import android.net.Uri
import com.sumin.vknewsclient.domain.model.post.FeedPostModel

sealed class Screen(val route: String) {

    data object NewsFeed : Screen(ROUTE_NEWS_FEED)
    data object Favourite : Screen(ROUTE_FAVOURITE)
    data object Profile : Screen(ROUTE_PROFILE)
    data object Comments : Screen(ROUTE_COMMENTS) {

        private const val ROUTE_FOR_ARGS = "comments"
        fun getRouteWithArgs(feedPost: FeedPostModel): String {
            return "$ROUTE_FOR_ARGS/${feedPost.id}/${feedPost.contentText.encode()}"
        }
    }

    data object Home : Screen(ROUTE_HOME)

    fun String.encode(): String {
        return Uri.encode(this)
    }


    companion object {

        const val KEY_FEED_POST_ID = "feed_post_id"
        const val KEY_CONTENT_TEXT = "content_text"

        const val ROUTE_HOME = "home"
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST_ID}/{$KEY_CONTENT_TEXT}"
        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
    }
}