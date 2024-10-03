package com.sumin.vknewsclient.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sumin.vknewsclient.presentation.NewsFeedApplication
import com.sumin.vknewsclient.presentation.ViewModelFactory
import com.sumin.vknewsclient.presentation.login.LoginScreen
import com.sumin.vknewsclient.presentation.login.LoginState
import com.sumin.vknewsclient.presentation.login.LoginViewModel
import com.sumin.vknewsclient.presentation.ui.theme.VkNewsClientTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val component by lazy {
        (application as NewsFeedApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {
                val viewModel: LoginViewModel = viewModel(factory = factory)
                val state = viewModel.authState.collectAsState(LoginState.Initial)
                val launcher =
                    rememberLauncherForActivityResult(
                        contract = VK.getVKAuthActivityResultContract()
                    ) {
                        viewModel.authResult(it)
                    }
                when (state.value) {
                    is LoginState.Authorized -> {
                        MainScreen(factory = factory)
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