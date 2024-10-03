package com.sumin.vknewsclient.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.sumin.vknewsclient.di.AppComponent
import com.sumin.vknewsclient.di.DaggerAppComponent

class NewsFeedApplication : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}

/**
 * Предоставляет component внутри composable функций
 */
@Composable
fun getApplicationComponent(): AppComponent {
    Log.d("Recomposition", "getApplicationComponent")
    return (LocalContext.current.applicationContext as NewsFeedApplication).component
}
