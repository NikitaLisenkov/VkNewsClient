package com.sumin.vknewsclient.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.presentation.getApplicationComponent
import com.sumin.vknewsclient.presentation.ui.theme.DarkBlue


@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentClick: (FeedPostModel) -> Unit
) {
    val component = getApplicationComponent()
    val viewModel: NewsFeedViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState(NewsFeedScreenState.Initial)

    NewsFeedScreenContent(
        screenState = screenState,
        paddingValues = paddingValues,
        onCommentClick = onCommentClick,
        viewModel = viewModel
    )
}


@Composable
private fun NewsFeedScreenContent(
    screenState: State<NewsFeedScreenState>,
    paddingValues: PaddingValues,
    onCommentClick: (FeedPostModel) -> Unit,
    viewModel: NewsFeedViewModel
) {
    when (val currentState = screenState.value) {

        is NewsFeedScreenState.Initial -> {}
        is NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }

        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                posts = currentState.posts,
                viewModel = viewModel,
                paddingValues = paddingValues,
                onCommentClick = onCommentClick,
                nextDataIsLoading = currentState.nextDataIsLoading
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    posts: List<FeedPostModel>,
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    onCommentClick: (FeedPostModel) -> Unit,
    nextDataIsLoading: Boolean
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
                        onLikeClick = { _ ->
                            viewModel.changeLikeStatus(feedPost)
                        },
                        onCommentClick = {
                            onCommentClick(feedPost)
                        }
                    )
                }
            )
        }
        item {
            if (nextDataIsLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            } else {
                SideEffect {
                    viewModel.loadNextRecommendations()
                }
            }
        }
    }
}
