package com.example.networkex.gson.reference.model

import com.google.gson.annotations.SerializedName

data class OAuthTokenResponse(
        @SerializedName("access_token")
        val accessToken: String? = null,
        @SerializedName("refresh_token")
        val refreshToken: String? = null,
        @SerializedName("token_type")
        val tokenType: String? = null,
        @SerializedName("expires_in")
        val expiresIn: Int? = null,
        val scope: String? = null,
        val deviceId: String? = null,
        @SerializedName("SESSION")
        val session: String? = null,
        val jti: String? = null,
        val header: List<MISResponseHeader>? = null,
        val body: List<MISUserLoginResponseBody>? = null,
)

data class MISResponseHeader(
        @SerializedName("MSGTYPE")
        val msgType: String,
        @SerializedName("TransactionID")
        val transactionId: String? = null,
        @SerializedName("RESULT")
        val result: String,
        @SerializedName("RESULTCD")
        val resultCd: String,
        @SerializedName("RESULTMSG")
        val resultMsg: String
)

data class MISUserLoginResponseBody(
        @SerializedName("try_cnt")
        val tryCnt: String? = null,
        @SerializedName("pw_upddt")
        val pwUpddt: String? = null,
)