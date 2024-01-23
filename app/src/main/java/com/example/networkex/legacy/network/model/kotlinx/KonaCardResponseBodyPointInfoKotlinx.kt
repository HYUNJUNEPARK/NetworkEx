package com.example.networkex.legacy.network.model.kotlinx

import kotlinx.serialization.Serializable

@Serializable
data class KonaCardResponseBodyPointInfoKotlinx(
    val pointAmount: Int? = null,
    val pointType: String? = null,
    val policyId: String? = null,
    val response: KonaCardResponseBodyPointInfoResponse?
)

@Serializable
data class KonaCardResponseBodyPointInfoResponse(
    val code: String? = null,
    val description: String? = null
)