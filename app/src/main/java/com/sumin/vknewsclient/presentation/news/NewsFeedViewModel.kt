package com.sumin.vknewsclient.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sumin.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.model.post.StatisticItem
import com.sumin.vknewsclient.domain.usecase.ChangeLikeUseCase
import com.sumin.vknewsclient.domain.usecase.DeletePostUseCase
import com.sumin.vknewsclient.domain.usecase.GetRecommendationsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val initState = NewsFeedScreenState.Initial

    private val _screenState: MutableStateFlow<NewsFeedScreenState> =
        MutableStateFlow(initState)
    val screenState: StateFlow<NewsFeedScreenState> = _screenState.asStateFlow()

    private val repo = NewsFeedRepositoryImpl(application)

    private val getRecommendationsUseCase = GetRecommendationsUseCase(repo)
    private val deletePostUseCase = DeletePostUseCase(repo)
    private val changeLikeUseCase = ChangeLikeUseCase(repo)

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