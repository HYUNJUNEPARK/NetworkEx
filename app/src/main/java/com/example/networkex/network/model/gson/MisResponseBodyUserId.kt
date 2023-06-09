package com.example.networkex.network.model.gson

data class MisResponseBodyUserId(
    val code: String?,
    val message: String?,
    val result: UserIdResult?,
    val status: Int?
)

data class UserIdResult(
    val grade: String?,
    val loginId: String?,
    val userId: String?
)