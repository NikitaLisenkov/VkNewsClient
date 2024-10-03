package com.sumin.vknewsclient.presentation

import android.app.Application
import com.sumin.vknewsclient.di.AppComponent
import com.sumin.vknewsclient.di.DaggerAppComponent

class NewsFeedApplication : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}