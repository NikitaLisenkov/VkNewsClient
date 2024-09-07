package com.sumin.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (feedId: Int) -> Unit
) {
    navigation(startDestination = Screen.NewsFeed.route, route = Screen.Home.route) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val feedId = Screen.Comments.getArgs(it.arguments)
            commentsScreenContent(feedId)
        }
    }
}