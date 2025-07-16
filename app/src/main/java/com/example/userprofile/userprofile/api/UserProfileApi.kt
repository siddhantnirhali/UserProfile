package com.example.userprofile.userprofile.api

import com.example.userprofile.BuildConfig
import com.example.userprofile.userprofile.model.UserProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserProfileApi {
    @GET("sesapi/navigation")
    suspend fun getUserProfileData(
        @Query("restApi") restApi: String = "Sesapi",
        @Query("sesapi_platform") platform: Int = 1,
        @Query("auth_token") authToken: String = BuildConfig.AUTH_TOKEN
    ): Response<UserProfileResponse>
}