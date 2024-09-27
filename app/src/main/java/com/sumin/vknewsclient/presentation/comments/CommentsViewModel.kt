package com.sumin.vknewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumin.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.usecase.GetCommentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommentsViewModel(
    feedPost: FeedPostModel,
    application: Application
) : ViewModel() {

    private val _screenState: MutableStateFlow<CommentsScreenState> =
        MutableStateFlow(CommentsScreenState.Initial)
    val screenState: StateFlow<CommentsScreenState> = _screenState.asStateFlow()

    private val repo = NewsFeedRepositoryImpl(application)

    private val getCommentsUseCase = GetCommentsUseCase(repo)

    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPostModel) {
        viewModelScope.launch {
            val comments = getCommentsUseCase(feedPost)
            _screenState.value = CommentsScreenState.Comments(
                post = feedPost,
                comments = comments
            )
        }
    }
}