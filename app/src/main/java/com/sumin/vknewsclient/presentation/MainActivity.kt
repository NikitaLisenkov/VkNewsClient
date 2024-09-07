package com.sumin.vknewsclient.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import com.sumin.vknewsclient.di.AppComponent
import com.sumin.vknewsclient.presentation.ui.theme.VkNewsClientTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    private val component = AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                val launcher =
                    rememberLauncherForActivityResult(
                        contract = VK.getVKAuthActivityResultContract()
                    ) {
                        when (it) {
                            is VKAuthenticationResult.Failed -> {
                                Log.d("MainActivity", "Failed auth")
                            }

                            is VKAuthenticationResult.Success -> {
                                Log.d("MainActivity", "Success auth")
                            }
                        }
                    }
                SideEffect {
                    launcher.launch(listOf(VKScope.WALL))
                }
                MainScreen(
                    component = component
                )
            }
        }
    }
}