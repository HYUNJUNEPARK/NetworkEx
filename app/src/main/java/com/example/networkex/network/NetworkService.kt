package com.example.networkex.network

import com.example.networkex.const.UrlConst.OAUTH
import com.example.networkex.const.UrlConst.POINT_INFO
import com.example.networkex.const.UrlConst.USER_ID
import com.example.networkex.model.KonaCardRequestBodyUserId
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
}