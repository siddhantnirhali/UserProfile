package com.example.userprofile.userprofile.data

import com.example.network.model.result.ApiResult
import com.example.network.model.result.toApiResult
import com.example.userprofile.userprofile.api.UserProfileApi
import com.example.userprofile.userprofile.domain.UserProfileRepository
import com.example.userprofile.userprofile.model.UserProfileResponse
import java.io.IOException

class UserProfileRepositoryImpl(private val userProfileApi: UserProfileApi) : UserProfileRepository {
    override suspend fun getUserProfileData(): Result<UserProfileResponse> {
        return try {
            val response = userProfileApi.getUserProfileData()
            return when (val result = response.toApiResult()) {
                is ApiResult.Success -> {
                    Result.success(result.data)
                }

                is ApiResult.Error -> {
                    Result.failure(Exception("Error ${result.code}: ${result.message}"))
                }

                is ApiResult.NetworkError -> {
                    Result.failure(Exception("Network error occurred"))
                }
            }
        }catch (e: IOException) {
            // Network-related error (like no internet)
            Result.failure(Exception("Network error occurred. Please check your connection."))
        } catch (e: Exception) {
            // Other unexpected errors
            Result.failure(Exception("Unexpected error occurred."))
        }
    }
}