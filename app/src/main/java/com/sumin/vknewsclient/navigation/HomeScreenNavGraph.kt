package com.sumin.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.sumin.vknewsclient.domain.model.post.FeedPostModel

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
            arguments = listOf(navArgument(Screen.KEY_FEED_POST_ID) {
                type = NavType.IntType
            }, navArgument(Screen.KEY_CONTENT_TEXT) {
                type = NavType.StringType
            })
        ) { entry ->  //comments/{feed_post_id}
            val feedPostId = entry.arguments?.getInt(Screen.KEY_FEED_POST_ID) ?: 0
            val feedPostContentText = entry.arguments?.getString(Screen.KEY_CONTENT_TEXT) ?: ""
            commentsScreenContent(
                FeedPostModel(
                    id = feedPostId,
                    contentText = feedPostContentText
                )
            )
        }
    }
}