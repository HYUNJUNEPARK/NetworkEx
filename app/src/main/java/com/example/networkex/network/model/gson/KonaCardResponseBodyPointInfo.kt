package com.example.networkex.network.model.gson


import com.google.gson.annotations.SerializedName

data class KonaCardResponseBodyPointInfo(
    @SerializedName("pointAmount")
    val pointAmount: Int?,
    @SerializedName("pointType")
    val pointType: String?,
    @SerializedName("policyId")
    val policyId: String?,
    @SerializedName("response")
    val response: KonaCardResponseBodyPointInfoResponse?
)

data class KonaCardResponseBodyPointInfoResponse(
    @SerializedName("code")
    val code: String?,
    @SerializedName("description")
    val description: String?
)