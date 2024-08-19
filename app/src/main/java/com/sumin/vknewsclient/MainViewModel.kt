package com.sumin.vknewsclient

import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.StatisticItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _feedPost: MutableStateFlow<FeedPost> = MutableStateFlow(FeedPost())
    val feedPost: StateFlow<FeedPost> = _feedPost.asStateFlow()

    fun updateCount(item: StatisticItem) {
        val oldStatistic = feedPost.value.statistics

        val newStatistic = oldStatistic.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        _feedPost.value = feedPost.value.copy(statistics = newStatistic)
    }
}