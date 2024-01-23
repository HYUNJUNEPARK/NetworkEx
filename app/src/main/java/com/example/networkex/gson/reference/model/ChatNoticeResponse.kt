package com.example.networkex.gson.reference.model

import com.google.gson.annotations.SerializedName

data class ChatNoticeResponse(
    @SerializedName("data")
    val data: NoticeData,
    @SerializedName("response")
    val response: ResponseBody
)

data class NoticeData(
    @SerializedName("notices")
    val notices: List<ChatNotice?>,
    @SerializedName("pageResponse")
    val pageResponse: PageResponse
)

data class ChatNotice(
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("endDate")
    val endDate: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("startDate")
    val startDate: String? = null,
    @SerializedName("title")
    val title: String? = null,
    var isFold: Boolean = true
)

data class PageResponse(
    @SerializedName("currentPage")
    val currentPage: Int? = null,
    @SerializedName("numberOfElements")
    val numberOfElements: Int? = null,
    @SerializedName("size")
    val size: Int? = null,
    @SerializedName("totalElements")
    val totalElements: Int? = null,
    @SerializedName("totalPages")
    val totalPages: Int? = null
)
