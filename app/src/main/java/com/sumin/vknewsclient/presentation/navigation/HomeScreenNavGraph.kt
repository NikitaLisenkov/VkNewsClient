package com.sumin.vknewsclient.presentation.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.sumin.vknewsclient.domain.post.FeedPostModel

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPostModel) -> Unit
) {
    navigation(startDestination = Screen.NewsFeed.route, route = Screen.Home.route) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(navArgument(Screen.KEY_FEED_POST) {
                type = FeedPostModel.navigationType
            })
        ) {
            val args = it.arguments
            val feedPost = kotlin.runCatching {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    args?.getParcelable(Screen.KEY_FEED_POST)
                } else {
                    args?.getParcelable(
                        Screen.KEY_FEED_POST,
                        FeedPostModel::class.java
                    )
                }
            }.getOrNull() ?: throw ClassCastException("Args is null")
            commentsScreenContent(feedPost)
        }
    }
}