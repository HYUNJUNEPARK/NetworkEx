package com.example.networkex.network

import com.example.networkex.const.UrlConst.OAUTH
import com.example.networkex.const.UrlConst.USER_ID
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
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