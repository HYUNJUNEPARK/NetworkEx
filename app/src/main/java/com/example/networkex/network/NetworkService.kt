package com.example.networkex.network

import com.example.networkex.const.UrlConst.OAUTH
import com.example.networkex.const.UrlConst.POINT_INFO
import com.example.networkex.const.UrlConst.SELF_CARE_V1
import com.example.networkex.const.UrlConst.SELF_CARE_V2
import com.example.networkex.const.UrlConst.USER_ID
import com.example.networkex.network.model.KonaCardRequestBodyUserId
import com.example.networkex.network.model.SelfRequestBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    //KONA CARD
    @POST(POINT_INFO)
    fun pointInfo(
        @Body
        body: KonaCardRequestBodyUserId
    ): Call<Any>

    //MIS
    @Multipart
    @POST(OAUTH)
    fun getAccessToken(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<Any>

    @GET("${USER_ID}/{id}")
    fun getUserMembershipId(
        @Path("id")
        id : String
    ): Call<Any>

    //SELF_CARE
    @POST(SELF_CARE_V1)
    fun selfcareV1(
        @Body
        body: SelfRequestBody
    ): Call<Any>

    @POST(SELF_CARE_V2) ////SELF08, 09, 12, 24
    fun selfcareV2(
        @Body
        body: SelfRequestBody
    ): Call<Any>
}