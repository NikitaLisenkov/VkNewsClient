package com.sumin.vknewsclient.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sumin.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.sumin.vknewsclient.domain.post.FeedPostModel
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

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadRecommendations()
    }


    private fun loadRecommendations() {
        viewModelScope.launch {
            val feedPosts = repo.loadPosts()
            _screenState.value = NewsFeedScreenState.Posts(posts = feedPosts)
        }
    }


    fun loadNextRecommendations() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repo.feedPosts,
            nextDataIsLoading = true
        )
        loadRecommendations()
    }


    fun deletePost(feedPost: FeedPostModel) {
        viewModelScope.launch {
            repo.deleteItem(feedPost)
            _screenState.value = NewsFeedScreenState.Posts(posts = repo.feedPosts)
        }
    }


    fun changeLikeStatus(feedPost: FeedPostModel) {
        viewModelScope.launch {
            repo.changeLike(feedPost)
            _screenState.value = NewsFeedScreenState.Posts(posts = repo.feedPosts)
        }
    }


//    fun updateCount(postModel: FeedPostModel, item: StatisticItem) {
//        val currentState = _screenState.value
//        if (currentState !is NewsFeedScreenState.Posts) return
//        val oldPosts = currentState.posts.toMutableList()
//        val oldStatistic = postModel.statistics
//        val newStatistic = oldStatistic.toMutableList().apply {
//            replaceAll { oldItem ->
//                if (oldItem.type == item.type) {
//                    oldItem.copy(count = oldItem.count + 1)
//                } else {
//                    oldItem
//                }
//            }
//        }
//        val newFeedPost = postModel.copy(statistics = newStatistic)
//        val newPosts = oldPosts.apply {
//            replaceAll {
//                if (it.id == newFeedPost.id) {
//                    newFeedPost
//                } else {
//                    it
//                }
//            }
//        }
//        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
//    }
}