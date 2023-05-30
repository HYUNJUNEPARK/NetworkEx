package com.example.networkex.network.model

import com.google.gson.annotations.SerializedName

data class MisResponseBodyUserId(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: UserIdResult?,
    @SerializedName("status")
    val status: Int?
)

data class UserIdResult(
    @SerializedName("grade")
    val grade: String?,
    @SerializedName("loginId")
    val loginId: String?,
    @SerializedName("userId")
    val userId: String?
)