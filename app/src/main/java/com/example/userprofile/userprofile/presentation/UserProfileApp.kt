package com.example.userprofile.userprofile.presentation

import androidx.compose.runtime.Composable
import com.example.network.core.Container

@Composable
fun UserProfileApp(container: Container) {

    val viewModel = container.provideUserProfileScreenViewModel()
    MenuScreen(viewModel)
}