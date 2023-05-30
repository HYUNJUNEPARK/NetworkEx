package com.example.networkex.network.model

import com.google.gson.annotations.SerializedName

data class SelfResponseBody04(
    @SerializedName("body")
    val body: List<SelfResponseInnerBody04?>?,
    @SerializedName("header")
    val header: List<SelfResponseInnerHeader04?>?
)

data class SelfResponseInnerHeader04(
    @SerializedName("MSGTYPE")
    val msgType: String?,
    @SerializedName("RESULT")
    val result: String?,
    @SerializedName("RESULTCD")
    val resultCd: String?,
    @SerializedName("RESULTMSG")
    val resultMsg: String?
)

data class SelfResponseInnerBody04(
    @SerializedName("DATA")
    val data: DATA04?,
    @SerializedName("MONA_DATA")
    val monaData: MONADATA?
)

data class DATA04(
    @SerializedName("acntOwrnBtDt")
    val acntOwrnBtDt: String?,
    @SerializedName("bankAcntNo")
    val bankAcntNo: String?,
    @SerializedName("bankCd")
    val bankCd: String?,
    @SerializedName("bankNm")
    val bankNm: String?,
    @SerializedName("bsRegNo")
    val bsRegNo: String?,
    @SerializedName("cardNo")
    val cardNo: String?,
    @SerializedName("cardValdEndYymm")
    val cardValdEndYymm: String?,
    @SerializedName("cdcmpCd")
    val cdcmpCd: String?,
    @SerializedName("cdcmpNm")
    val cdcmpNm: String?,
    @SerializedName("custDvCd")
    val custDvCd: String?,
    @SerializedName("custKdCd")
    val custKdCd: String?,
    @SerializedName("custNm")
    val custNm: String?,
    @SerializedName("custrnmBday")
    val custrnmBday: String?,
    @SerializedName("ipinCi")
    val ipinCi: String?,
    @SerializedName("pymAcntNo")
    val pymAcntNo: String?,
    @SerializedName("pymMthdCd")
    val pymMthdCd: String?,
    @SerializedName("pymMthdNm")
    val pymMthdNm: String?,
    @SerializedName("sysCreationDate")
    val sysCreationDate: String?,
    @SerializedName("sysUpdateDate")
    val sysUpdateDate: String?
)

data class MONADATA(
    @SerializedName("LAST_BLCD")
    val lastBlcd: String?,
    @SerializedName("LAST_BLMETHOD")
    val lastBlmethod: String?,
    @SerializedName("LAST_BLMETHODNM")
    val lastBlmethodnm: String?,
    @SerializedName("LAST_BLNM")
    val lastBlnm: String?,
    @SerializedName("LAST_BLNUM")
    val lastBlnum: String?
)