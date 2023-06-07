package com.example.networkex.network.model.gson

import com.google.gson.annotations.SerializedName

data class KonaCardRequestBodyUserId (
    @SerializedName("userId")
    val userId : String
)