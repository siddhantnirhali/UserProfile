package com.example.network.model.result

sealed class ApiResult<out T> {
    data class Success<T>(val data: T): ApiResult<T>()
    data class Error(val message: String, val code: Int? = null, val rawError: Any? = null): ApiResult<Nothing>()
    object NetworkError: ApiResult<Nothing>()
}