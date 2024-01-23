package com.example.networkex.gson.reference.model

import com.google.gson.annotations.SerializedName

data class DeviceInfoResponse(
    @SerializedName("data")
    val data: DeviceInfoData,
    @SerializedName("response")
    val response: CommonResponse
)

data class DeviceInfoData(
    @SerializedName("deviceId")
    val deviceId: String? = null,
    @SerializedName("loginId")
    val loginId: String? = null,
    @SerializedName("mdn")
    val mdn: String? = null,
    @SerializedName("publicKey")
    val publicKey: String? = null
)

