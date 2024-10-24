package com.sumin.vknewsclient.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.sumin.vknewsclient.di.app.AppComponent
import com.sumin.vknewsclient.di.app.DaggerAppComponent

class NewsFeedApplication : Application() {

    val component: AppComponent = DaggerAppComponent.factory().create(this)
}

/**
 * Предоставляет component внутри composable функций
 */
@Composable
fun getApplicationComponent(): AppComponent {
    return (LocalContext.current.applicationContext as NewsFeedApplication).component
}
