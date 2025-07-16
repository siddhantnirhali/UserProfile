package com.example.network.model.result

import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Response

fun <T> Response<T>.toApiResult(): ApiResult<T> {
    return if (isSuccessful) {
        body()?.let {
            ApiResult.Success(it)
        } ?: ApiResult.Error("Empty response body", code())
    } else {
        val errorMessage = try {
            val errorJson = errorBody()?.string()

            // Try Weather API format
            val apiError = Gson().fromJson(errorJson, ApiError::class.java)
            apiError.error.message
        } catch (e: Exception) {
            try {
                // Try common fallback format
                val fallback = JSONObject(errorBody()?.string() ?: "")
                when {
                    fallback.has("message") -> fallback.getString("message")
                    fallback.has("error_message") -> fallback.getString("error_message")
                    else -> "Unexpected error occurred"
                }
            } catch (e: Exception) {
                "Something went wrong"
            }
        }

        ApiResult.Error(errorMessage, code())
    }
}
