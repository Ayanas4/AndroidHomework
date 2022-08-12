package com.example.androidhomework.model

data class UserinfoModel(
    val status: Int,
    val user_info: UserInfo
)

data class UserInfo(
    val mail: String,
    val first_name: String,
    val last_name: String

)