package com.sumin.vknewsclient.presentation.comment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.comment.PostComment
import com.sumin.vknewsclient.domain.post.FeedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CommentsViewModel(
    feedId: Int,
    private val repository: FeedRepository
) : ViewModel() {

    private val _screenState: MutableStateFlow<CommentsScreenState> = MutableStateFlow(CommentsScreenState.Initial)
    val screenState: StateFlow<CommentsScreenState> = _screenState.asStateFlow()

    init {
        loadComments(feedId)
    }

    private fun loadComments(feedId: Int) {
        Log.d("qwe", "Opened $feedId")
        val feedPost = repository.cachedFeed ?: return
        val comments = List(20) {
            PostComment(id = it)
        }
        _screenState.value = CommentsScreenState.Comments(
            post = feedPost,
            comments = comments
        )
    }
}