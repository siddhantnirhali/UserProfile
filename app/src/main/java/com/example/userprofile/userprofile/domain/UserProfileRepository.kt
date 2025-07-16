package com.example.userprofile.userprofile.domain

import com.example.userprofile.userprofile.model.UserProfileResponse

interface UserProfileRepository {
    suspend fun getUserProfileData(): Result<UserProfileResponse>
}