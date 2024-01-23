package com.example.networkex.legacy.network.model.gson

import com.google.gson.annotations.SerializedName

data class SelfResponseBody04(
    val body: List<SelfResponseInnerBody04?>?,
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
    val acntOwrnBtDt: String?,
    val bankAcntNo: String?,
    val bankCd: String?,
    val bankNm: String?,
    val bsRegNo: String?,
    val cardNo: String?,
    val cardValdEndYymm: String?,
    val cdcmpCd: String?,
    val cdcmpNm: String?,
    val custDvCd: String?,
    val custKdCd: String?,
    val custNm: String?,
    val custrnmBday: String?,
    val ipinCi: String?,
    val pymAcntNo: String?,
    val pymMthdCd: String?,
    val pymMthdNm: String?,
    val sysCreationDate: String?,
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