package com.sumin.vknewsclient.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sumin.vknewsclient.data.mapper.NewsFeedMapper
import com.sumin.vknewsclient.di.ServiceLocator
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.model.post.StatisticItem
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val initState = NewsFeedScreenState.Initial

    private val _screenState: MutableStateFlow<NewsFeedScreenState> =
        MutableStateFlow(initState)
    val screenState: StateFlow<NewsFeedScreenState> = _screenState.asStateFlow()

    private val mapper = NewsFeedMapper()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            val storage = VKPreferencesKeyValueStorage(getApplication())
            val token = VKAccessToken.restore(storage) ?: return@launch
            val response = ServiceLocator.api.loadNewsFeed(token.accessToken)
            val feedPosts = mapper.mapDtoToDomain(response)
            _screenState.value = NewsFeedScreenState.Posts(posts = feedPosts)
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


    fun deletePost(post: FeedPostModel) {
        val currentState = _screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return
        val modifiedList = currentState.posts.toMutableList()
        modifiedList.remove(post)
        _screenState.value = NewsFeedScreenState.Posts(posts = modifiedList)
    }
}