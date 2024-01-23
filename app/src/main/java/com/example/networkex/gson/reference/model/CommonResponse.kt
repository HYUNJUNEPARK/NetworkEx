package com.example.networkex.gson.reference.model

import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("response")
    val response: ResponseBody? = null
)

data class ResponseBody(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String
)