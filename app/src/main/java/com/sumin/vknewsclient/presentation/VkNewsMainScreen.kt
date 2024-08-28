package com.sumin.vknewsclient.presentation

import androidx.compose.foundation.clickable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.navigation.AppNavGraph
import com.sumin.vknewsclient.navigation.rememberNavState
import com.sumin.vknewsclient.presentation.comment.ScreenComments
import com.sumin.vknewsclient.presentation.post.ScreenHome


@Composable
fun MainScreen() {
    val navState = rememberNavState()

    val commentToPost: MutableState<FeedPostModel?> = remember {
        mutableStateOf(null)
    }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navState.navHostController.currentBackStackEntryAsState()
                val currentRout = navBackStackEntry?.destination?.route
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )

                items.forEach { item ->
                    BottomNavigationItem(
                        selected = currentRout == item.screen.route,
                        onClick = { navState.navigateTo(item.screen.route) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navState.navHostController,
            homeScreenContent = {
                if (commentToPost.value == null) {
                    ScreenHome(
                        paddingValues = paddingValues,
                        onCommentClick = {
                            commentToPost.value = it
                        }
                    )
                } else {
                    ScreenComments {
                        commentToPost.value = null
                    }
                }
            },
            favouriteScreenContent = {
                TextCounter(name = "Favourite")
            },
            profileScreenContent = {
                TextCounter(name = "Profile")
            })
    }
}


@Composable
private fun TextCounter(name: String) {
    var count by rememberSaveable {
        mutableStateOf(0)
    }

    Text(
        text = "$name Count: $count",
        modifier = Modifier.clickable { count++ },
        color = Color.Black
    )
}