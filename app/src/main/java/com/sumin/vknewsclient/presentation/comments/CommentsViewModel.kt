package com.sumin.vknewsclient.presentation.comments

import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.comment.PostComment
import com.sumin.vknewsclient.domain.post.FeedPostModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CommentsViewModel(feedPost: FeedPostModel) : ViewModel() {

    private val _screenState: MutableStateFlow<CommentsScreenState> =
        MutableStateFlow(CommentsScreenState.Initial)
    val screenState: StateFlow<CommentsScreenState> = _screenState.asStateFlow()

    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPostModel) {
        val comments = List(20) {
            PostComment(id = it)
        }
        _screenState.value = CommentsScreenState.Comments(
            post = feedPost,
            comments = comments
        )
    }
}