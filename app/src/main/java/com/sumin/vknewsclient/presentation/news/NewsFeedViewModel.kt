package com.sumin.vknewsclient.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.model.post.StatisticItem
import com.sumin.vknewsclient.domain.usecase.ChangeLikeUseCase
import com.sumin.vknewsclient.domain.usecase.DeletePostUseCase
import com.sumin.vknewsclient.domain.usecase.GetRecommendationsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val changeLikeUseCase: ChangeLikeUseCase
) :
    ViewModel() {

    private val initState = NewsFeedScreenState.Initial

    private val _screenState: MutableStateFlow<NewsFeedScreenState> =
        MutableStateFlow(initState)
    val screenState: StateFlow<NewsFeedScreenState> = _screenState.asStateFlow()

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadRecommendations()
    }


    private fun loadRecommendations() {
        viewModelScope.launch {
            val feedPosts = getRecommendationsUseCase()
            _screenState.value = NewsFeedScreenState.Posts(posts = feedPosts)
        }
    }


    fun loadNextRecommendations() {
        viewModelScope.launch {
            _screenState.value = NewsFeedScreenState.Posts(
                posts = getRecommendationsUseCase(),
                nextDataIsLoading = true
            )
        }
        loadRecommendations()
    }


    fun deletePost(feedPost: FeedPostModel) {
        viewModelScope.launch {
            deletePostUseCase(feedPost)
            _screenState.value = NewsFeedScreenState.Posts(posts = getRecommendationsUseCase())
        }
    }


    fun changeLikeStatus(feedPost: FeedPostModel) {
        viewModelScope.launch {
            changeLikeUseCase(feedPost)
            _screenState.value = NewsFeedScreenState.Posts(posts = getRecommendationsUseCase())
        }
    }


    fun updateCount(postModel: FeedPostModel, item: StatisticItem) {
        val currentState = _screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return
        val oldPosts = currentState.posts.toMutableList()
        val oldStatistic = postModel.statistics
        val newStatistic = oldStatistic.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newFeedPost = postModel.copy(statistics = newStatistic)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
    }
}