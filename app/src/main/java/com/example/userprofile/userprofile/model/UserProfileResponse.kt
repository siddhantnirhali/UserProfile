package com.example.userprofile.userprofile.model

data class UserProfileResponse(
    val result: Result = Result(),
    val session_id: String = ""
)

data class Result(
    val title: String = "",
    val user_photo: String = "",
    val wallet_amount: String = "",
    val wallet_url: String = "",
    val menus: List<MenuItem> = emptyList(),
    val notification_count: Int = 0,
    val friend_req_count: Int =0,
    val message_count: Int = 0,
    val loggedin_user_id: Int = 0
)

data class MenuItem(
    val type: Int,
    val module: String? = null,
    val label: String,
    val icon: String,
    val url: String,
    val `class`: String
)

