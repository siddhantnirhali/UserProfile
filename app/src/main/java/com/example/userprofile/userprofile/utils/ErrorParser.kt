package com.example.network.utils

import retrofit2.Response
import com.google.gson.Gson
import java.lang.Exception

object ErrorParser {
    inline fun <reified E> parseError(response: Response<*>): E? {
        return try {
            response.errorBody()?.string()?.let {
                Gson().fromJson(it, E::class.java)
            }
        } catch (e: Exception) {
            null
        }
    }
}
