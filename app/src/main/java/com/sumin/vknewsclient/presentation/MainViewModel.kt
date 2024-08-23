package com.sumin.vknewsclient.presentation

import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.model.FeedPostModel
import com.sumin.vknewsclient.domain.model.StatisticItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val newsList = mutableListOf<FeedPostModel>().apply {
        repeat(10) {
            add(FeedPostModel(id = it))
        }
    }

    private val _feedPosts: MutableStateFlow<List<FeedPostModel>> =
        MutableStateFlow(newsList)
    val feedPosts: StateFlow<List<FeedPostModel>> = _feedPosts.asStateFlow()


    fun updateCount(feedPostModel: FeedPostModel, item: StatisticItem) {
        val oldPost = _feedPosts.value.toMutableList()
        val oldStatistic = feedPostModel.statistics
        val newStatistic = oldStatistic.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newFeedPost = feedPostModel.copy(statistics = newStatistic)
        _feedPosts.value = oldPost.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
    }

    fun deletePost(model: FeedPostModel) {
        val modifiedList = _feedPosts.value.toMutableList()
        modifiedList.remove(model)
        _feedPosts.value = modifiedList
    }
}