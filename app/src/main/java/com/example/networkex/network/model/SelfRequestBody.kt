package com.example.networkex.network.model

import com.example.networkex.const.ApiConst.APP_TYPE
import com.example.networkex.const.ApiConst.ENT_ID
import com.google.gson.annotations.SerializedName

data class SelfRequestBody(
    @SerializedName("header")
    val header: List<SelfRequestInnerHeader>,
    @SerializedName("body")
    val body: List<SelfRequestInnerBody>
)

data class SelfRequestInnerBody(
    @SerializedName("apptype") //호출경로, 5자리 이하 String(ex. AOS, IOS, APP 등 사용)
    val apptype: String = APP_TYPE,
    @SerializedName("entid") //호출자 ID
    val entid: String? = ENT_ID,
    @SerializedName("entrNo") //계약번호 (mdn과 entrNo 중 하나 택일하여 필수로 지정)
    val entrNo: String = "",
    @SerializedName("mdn") //전화번호
    val mdn: String?,
    @SerializedName("callYyyymm") //통화년월, self24
    val callYyyymm: String? = null,
    @SerializedName("wrkTypeCd") //업무구분코드 self24
    val wrkTypeCd: String? = null,
)

data class SelfRequestInnerHeader(
    @SerializedName("ESBCD")
    val eSBCD: String?, //전문코드
    @SerializedName("MSGTYPE")
    val mSGTYPE: String //전문코드
)