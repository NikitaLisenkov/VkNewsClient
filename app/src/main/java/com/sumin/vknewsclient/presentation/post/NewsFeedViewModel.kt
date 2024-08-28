package com.sumin.vknewsclient.presentation.post

import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.model.post.FeedPostModel
import com.sumin.vknewsclient.domain.model.post.StatisticItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewsFeedViewModel : ViewModel() {

    private val sourceList = List(10) {
        FeedPostModel(id = it)
    }

    private val initState = NewsFeedScreenState.Posts(posts = sourceList)


    private val _screenState: MutableStateFlow<NewsFeedScreenState> =
        MutableStateFlow(initState)
    val screenState: StateFlow<NewsFeedScreenState> = _screenState.asStateFlow()


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