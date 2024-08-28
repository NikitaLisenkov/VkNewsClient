package com.sumin.vknewsclient.presentation

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sumin.vknewsclient.domain.model.FeedPostModel


@Composable
fun ScreenHome(viewModel: NewsFeedViewModel, paddingValues: PaddingValues) {

    val screenState = viewModel.screenState.collectAsState(NewsFeedScreenState.Initial)

    val currentState = screenState.value

    when (currentState) {
        is NewsFeedScreenState.Comments -> {
            ScreenComments(
                feedPostModel = currentState.feedPost,
                comments = currentState.comments,
                onBackPressed = {
                    viewModel.closeComments()
                }
            )
            BackHandler {
                viewModel.closeComments()
            }
        }

        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                posts = currentState.posts,
                viewModel = viewModel,
                paddingValues = paddingValues
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
    paddingValues: PaddingValues
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
        items(posts, key = { it.id }) { postModel ->

            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.deletePost(postModel)
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
                        feedPostModel = postModel,
                        onLikeClick = { statisticItem ->

                            viewModel.updateCount(postModel, statisticItem)
                        },
                        onCommentClick = {
                            viewModel.showComments(postModel)
                        },
                        onShareClick = { statisticItem ->
                            viewModel.updateCount(postModel, statisticItem)
                        },
                        onViewClick = { statisticItem ->
                            viewModel.updateCount(postModel, statisticItem)
                        }
                    )
                }
            )
        }
    }
}
