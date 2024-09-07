package com.sumin.vknewsclient.presentation.post

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sumin.vknewsclient.di.AppComponent
import com.sumin.vknewsclient.domain.post.FeedPostModel

@Composable
fun ScreenHome(
    paddingValues: PaddingValues,
    component: AppComponent,
    onCommentClick: (feedId: Int) -> Unit
) {
    val viewModel: NewsFeedViewModel = viewModel(factory = NewsViewModelFactory(component.feedRepository))

    val screenState by viewModel.screenState.collectAsState(NewsFeedScreenState.Initial)

    when (val state = screenState) {
        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                posts = state.posts,
                viewModel = viewModel,
                paddingValues = paddingValues,
                onCommentClick = {
                    viewModel.saveFeed(it)
                    onCommentClick.invoke(it.id)
                }
            )
        }

        is NewsFeedScreenState.Initial -> {}
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    posts: List<FeedPostModel>,
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    onCommentClick: (FeedPostModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(posts, key = { it.id }) { feedPost ->

            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.deletePost(feedPost)
            }
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                background = {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                            .background(Color.Red.copy(0.5f)),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "Delete item",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    }
                },
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = {
                    FractionalThreshold(0.5f)
                },
                dismissContent = {
                    PostCard(
                        feedPostModel = feedPost,
                        onLikeClick = { statisticItem ->

                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onCommentClick = {
                            onCommentClick(feedPost)
                        },
                        onShareClick = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onViewClick = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        }
                    )
                }
            )
        }
    }
}
