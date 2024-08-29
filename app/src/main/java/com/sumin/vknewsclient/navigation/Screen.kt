package com.sumin.vknewsclient.navigation

sealed class Screen(val route: String) {
    data object NewsFeed : Screen(ROUTE_NEWS_FEED)
    data object Favourite : Screen(ROUTE_FAVOURITE)
    data object Profile : Screen(ROUTE_PROFILE)
    data object Comments : Screen(ROUTE_COMMENTS)
    data object Home : Screen(ROUTE_HOME)

    private companion object {
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
        const val ROUTE_COMMENTS = "comments"
        const val ROUTE_HOME = "home"
    }
}