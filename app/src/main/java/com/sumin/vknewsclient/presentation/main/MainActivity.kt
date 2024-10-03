package com.sumin.vknewsclient.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sumin.vknewsclient.presentation.getApplicationComponent
import com.sumin.vknewsclient.presentation.login.LoginScreen
import com.sumin.vknewsclient.presentation.login.LoginState
import com.sumin.vknewsclient.presentation.login.LoginViewModel
import com.sumin.vknewsclient.presentation.ui.theme.VkNewsClientTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val component = getApplicationComponent()
            val viewModel: LoginViewModel = viewModel(factory = component.getViewModelFactory())
            val state = viewModel.authState.collectAsState(LoginState.Initial)
            val launcher =
                rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    viewModel.authResult(it)
                }

            VkNewsClientTheme {
                when (state.value) {
                    is LoginState.Authorized -> {
                        MainScreen()
                    }

                    is LoginState.NotAuthorized -> {
                        LoginScreen {
                            launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}