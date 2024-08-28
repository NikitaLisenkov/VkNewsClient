package com.sumin.vknewsclient.presentation.comment

import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.model.comment.PostComment
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CommentsViewModel : ViewModel() {

    private val _screenState: MutableStateFlow<CommentsScreenState> =
        MutableStateFlow(CommentsScreenState.Initial)
    val screenState: StateFlow<CommentsScreenState> = _screenState.asStateFlow()

    init {
        loadComments(FeedPostModel())
    }

    fun loadComments(feedPost: FeedPostModel) {
        val comments = List(20) {
            PostComment(id = it)
        }
        _screenState.value = CommentsScreenState.Comments(
            post = feedPost,
            comments = comments
        )
    }
}