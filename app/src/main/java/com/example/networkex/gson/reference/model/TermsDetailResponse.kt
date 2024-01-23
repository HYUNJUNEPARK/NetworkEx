package com.example.networkex.gson.reference.model

import com.google.gson.annotations.SerializedName

data class TermsDetailResponse(
    @SerializedName("data")
    val data: TermsDetailData,
    @SerializedName("response")
    val response: CommonResponse,
)

data class TermsDetailData(
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("title")
    val title: String? = null,
)