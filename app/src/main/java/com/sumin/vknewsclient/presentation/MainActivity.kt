package com.sumin.vknewsclient.presentation

//import com.sumin.vknewsclient.ui.theme.MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sumin.vknewsclient.presentation.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                MainScreen()
            }
        }
    }
}