package com.example.network.provider

import com.example.network.client.RetrofitClient
import com.example.userprofile.userprofile.api.UserProfileApi
import retrofit2.Retrofit

object NetworkProvider {

    private fun provideRetrofit(baseUrl: String, tokenProvider: () -> String?): Retrofit {
        return RetrofitClient.create(baseUrl, tokenProvider)
    }

    fun provideUserProfileApi(baseUrl: String, tokenProvider: () -> String?): UserProfileApi {
        return provideRetrofit(baseUrl, tokenProvider).create(UserProfileApi::class.java)
    }
}
