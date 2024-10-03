package com.sumin.vknewsclient.presentation

import android.app.Application
import com.sumin.vknewsclient.di.AppComponent
import com.sumin.vknewsclient.di.DaggerAppComponent
import com.sumin.vknewsclient.domain.model.post.FeedPostModel

class NewsFeedApplication : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(
                this, FeedPostModel(
                    id = 0,
                    communityId = 0,
                    communityName = "",
                    publicationDate = "",
                    communityImageUrl = "",
                    contentText = "",
                    contentImageUrl = "",
                    statistics = listOf(),
                    isLiked = false
                )
            )
    }
}