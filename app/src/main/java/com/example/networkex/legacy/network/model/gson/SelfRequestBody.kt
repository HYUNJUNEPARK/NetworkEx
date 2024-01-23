package com.example.networkex.legacy.network.model.gson

import com.example.networkex.legacy.const.ApiConst.APP_TYPE
import com.example.networkex.legacy.const.ApiConst.ENT_ID
import com.google.gson.annotations.SerializedName

data class SelfRequestBody(
    val header: List<SelfRequestInnerHeader>,
    val body: List<SelfRequestInnerBody>
)

data class SelfRequestInnerBody(
    val apptype: String = APP_TYPE, //호출경로, 5자리 이하 String(ex. AOS, IOS, APP 등 사용)
    val entid: String? = ENT_ID,
    val entrNo: String = "", //계약번호 (mdn과 entrNo 중 하나 택일하여 필수로 지정)
    val mdn: String?, //전화번호
    val callYyyymm: String? = null, //통화년월, self24
    val wrkTypeCd: String? = null //업무구분코드 self24
)

data class SelfRequestInnerHeader(
    @SerializedName("ESBCD")
    val esbcd: String?, //전문코드
    @SerializedName("MSGTYPE")
    val msgType: String //전문코드
)