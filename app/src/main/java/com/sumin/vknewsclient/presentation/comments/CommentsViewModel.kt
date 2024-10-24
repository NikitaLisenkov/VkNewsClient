package com.sumin.vknewsclient.presentation.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.usecase.GetCommentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val feedPost: FeedPostModel,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    private var offset = 0
    private var totalCount = -1

    private val _screenState: MutableStateFlow<CommentsScreenState> =
        MutableStateFlow(CommentsScreenState())
    val screenState: StateFlow<CommentsScreenState> = _screenState.asStateFlow()


    fun loadComments() {
        val allLoaded = _screenState.value.comments.size == totalCount
        val isLoading = _screenState.value.isLoading
        if (allLoaded || isLoading) return

        _screenState.update { state ->
            state.copy(isLoading = true)
        }

        viewModelScope.launch {
            val response = getCommentsUseCase.invoke(feedPost, offset)
            totalCount = response.totalCount

            _screenState.update { state ->
                val updatedComments = (state.comments + response.comments)
                    .distinctBy { comment ->
                        comment.id
                    }

                state.copy(
                    comments = updatedComments,
                    isLoading = false
                )
            }

            if (response.comments.isEmpty()) {
                totalCount = _screenState.value.comments.size
            }

            offset = _screenState.value.comments.size
        }
    }
}