package com.example.networkex.network.model

import com.google.gson.annotations.SerializedName

data class KonaCardRequestBodyUserId (
    @SerializedName("userId")
    val userId : String
)