package com.example.network.core

import android.content.Context
import com.example.network.provider.NetworkProvider
import com.example.userprofile.userprofile.data.UserProfileRepositoryImpl
import com.example.userprofile.userprofile.domain.UserProfileRepository
import com.example.userprofile.userprofile.presentation.UserProfileScreenViewModel

class Container(context: Context) {


    private val userProfileApi = NetworkProvider.provideUserProfileApi(
        baseUrl = "https://demo.socialnetworking.solutions/",
        tokenProvider = { null })

    private fun getToken() : String {
        return "B179086bb56c32731633335762"
    }


    private val userProfileRepositoryImpl: UserProfileRepository = UserProfileRepositoryImpl(userProfileApi)


    private val userProfileScreenViewModel by lazy {
        UserProfileScreenViewModel(userProfileRepositoryImpl)
    }

    fun provideUserProfileScreenViewModel() = userProfileScreenViewModel
}