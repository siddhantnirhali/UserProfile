package com.example.network.model.result

data class ApiError(
    val error: ErrorDetails
)

data class ErrorDetails(
    val message: String,
    val code: Int
)
