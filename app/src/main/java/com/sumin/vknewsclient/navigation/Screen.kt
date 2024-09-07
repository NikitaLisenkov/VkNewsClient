package com.sumin.vknewsclient.navigation

import android.os.Bundle

sealed class Screen(val route: String) {

    data object Favourite : Screen(ROUTE_FAVOURITE)
    data object Profile : Screen(ROUTE_PROFILE)
    data object Home : Screen(ROUTE_HOME)
    data object NewsFeed : Screen(ROUTE_NEWS_FEED)

    data object Comments : Screen(ROUTE_COMMENTS) {
        fun getRouteWithArgs(feedId: Int): String {
            return "$COMMENTS/$feedId"
        }

        fun getArgs(bundle: Bundle?): Int {
            requireNotNull(bundle)
            return bundle.getInt(KEY_FEED_ID)
        }
    }

    companion object {
        const val KEY_FEED_ID = "feed_post"
        const val COMMENTS = "comments"

        const val ROUTE_HOME = "home"
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_COMMENTS = "$COMMENTS/{$KEY_FEED_ID}"
        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
    }
}