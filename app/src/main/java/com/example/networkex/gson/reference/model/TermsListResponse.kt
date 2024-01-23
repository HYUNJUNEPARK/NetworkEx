package com.example.networkex.gson.reference.model

import com.google.gson.annotations.SerializedName

data class TermsListResponse(
    @SerializedName("data")
    val data: TermsListData,
    @SerializedName("response")
    val response: ResponseBody,
)

data class TermsListData(
    @SerializedName("terms")
    val termsList: List<Term>
)

data class Term(
    @SerializedName("consent")
    val consent: Boolean? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("required")
    val required: Boolean? = null,
    @SerializedName("title")
    val title: String? = null
)