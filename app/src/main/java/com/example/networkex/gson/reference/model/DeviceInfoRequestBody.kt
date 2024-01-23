package com.example.networkex.gson.reference.model

import com.google.gson.annotations.SerializedName

data class DeviceInfoRequestBody(
    @SerializedName("mdn")
    val deviceId: String,
)