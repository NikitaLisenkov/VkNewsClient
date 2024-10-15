package com.sumin.vknewsclient.presentation.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.sumin.vknewsclient.R
import com.sumin.vknewsclient.domain.model.profile.ProfileModel
import com.sumin.vknewsclient.presentation.getApplicationComponent
import com.sumin.vknewsclient.presentation.ui.theme.DarkBlue

@Preview
@Composable
fun ProfileScreen() {

    val component = getApplicationComponent()
    val viewModel: ProfileViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.profileState.collectAsState()
    val currentState = screenState.value

    when (currentState) {
        is ProfileScreenState.Loading -> {
            LoadingScreen()
        }

        is ProfileScreenState.Content -> {
            ProfileContent(currentState.profile)
        }

        is ProfileScreenState.Error -> {
            ErrorScreen()
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = DarkBlue)
    }
}

@Composable
private fun ErrorScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.something_went_wrong))
    }
}

@Composable
fun ProfileContent(profile: ProfileModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (profile.avatarUrl.isNotEmpty()) {
            AsyncImage(
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
                contentDescription = null,
                model = profile.avatarUrl,
            )
        }

        Text(
            text = "${profile.firstName} ${profile.lastName}",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(R.string.phone, profile.phone),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = stringResource(R.string.city, profile.homeTown),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = stringResource(R.string.status, profile.status),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = stringResource(R.string.b_date, profile.birthdayDate),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}