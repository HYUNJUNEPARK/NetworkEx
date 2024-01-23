package com.example.networkex.gson.reference.network

import com.example.networkex.gson.reference.model.ChatNoticeResponse
import com.example.networkex.gson.reference.model.CommonResponse
import com.example.networkex.gson.reference.model.DeviceInfoRequestBody
import com.example.networkex.gson.reference.model.DeviceInfoResponse
import com.example.networkex.gson.reference.model.OAuthTokenResponse
import com.example.networkex.gson.reference.model.TermsDetailResponse
import com.example.networkex.gson.reference.model.TermsListResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    /* requestBody
     *{
     *   "mdn" : "01090918849"
     * } */
    @POST("/chat/api/v1/device/data/get")
    fun checkDeviceData(
        @Body requestBody: DeviceInfoRequestBody
    ): Call<DeviceInfoResponse>

    @GET("/chat/api/v1/terms?location=join")
    fun requestTermsList(): Call<TermsListResponse>

    //baseUrl/chat/api/v1/terms/1
    @GET("/chat/api/v1/terms/{termsId}")
    fun requestTermsDetail(
        @Path("termsId") termsId: Int
    ): Call<TermsDetailResponse>

    @DELETE("/chat/api/v1/users")
    fun requestDeleteUser(): Call<CommonResponse>

    //baseUrl/chat/api/v1/notices?page=0&size=20
    @GET("/chat/api/v1/notices")
    fun requestChatNotice(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<ChatNoticeResponse>

    //oaouthTokenApiEx.png 참고
    @Multipart
    @POST("/oauth/token")
    fun getAccessToken(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<OAuthTokenResponse>
}