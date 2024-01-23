package com.example.networkex.legacy.network.model.gson

data class KonaCardResponseBodyPointInfo(
    val pointAmount: Int?,
    val pointType: String?,
    val policyId: String?,
    val response: KonaCardResponseBodyPointInfoResponse?
)

data class KonaCardResponseBodyPointInfoResponse(
    val code: String?,
    val description: String?
)