package com.example.networkex.network.gson

import com.example.networkex.const.ApiConst.SELF04_CODE
import com.example.networkex.const.ApiConst.SELF04_HEADER_MSG_TYPE
import com.example.networkex.network.model.gson.KonaCardRequestBodyUserId
import com.example.networkex.network.model.gson.SelfRequestBody
import com.example.networkex.network.model.gson.SelfRequestInnerBody
import com.example.networkex.network.model.gson.SelfRequestInnerHeader
import com.example.networkex.network.NetworkInterceptor.commonInterceptor
import com.example.networkex.network.NetworkInterceptor.konaCardInterceptor
import com.example.networkex.network.NetworkInterceptor.misAuthInterceptor
import com.example.networkex.network.NetworkProvider.provideRetrofit
import com.example.networkex.util.NetworkUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class NetworkManagerGson {
    //KONA CARD
    fun requestMembershipPoint(membershipId: String): Response<Any> {
        val networkService: NetworkServiceGson = provideRetrofit(konaCardInterceptor).create(
            NetworkServiceGson::class.java)
        return networkService.pointInfo(KonaCardRequestBodyUserId(membershipId)).execute()
    }

    //MIS
    fun requestAccessToken(userId: String, pwd: String, deviceId: String, pushToken: String): Response<Any> {
        val networkService: NetworkServiceGson = provideRetrofit(misAuthInterceptor).create(
            NetworkServiceGson::class.java)
        val mediaType = "text/plain"
        val requestMap: HashMap<String, RequestBody> = HashMap()
        requestMap.apply {
            this["grant_type"] = "password".toRequestBody(mediaType.toMediaTypeOrNull())
            this["grant_type"] = "password".toRequestBody(mediaType.toMediaTypeOrNull())
            this["scope"] = "webclient".toRequestBody(mediaType.toMediaTypeOrNull())
            this["username"] = userId.toRequestBody(mediaType.toMediaTypeOrNull())
            this["password"] = pwd.toRequestBody(mediaType.toMediaTypeOrNull())
            this["TransactionID"] = NetworkUtil.getTransactionId().toRequestBody(mediaType.toMediaTypeOrNull())
            this["deviceId"] = deviceId.toRequestBody(mediaType.toMediaTypeOrNull())
            this["pushToken"] = pushToken.toRequestBody(mediaType.toMediaTypeOrNull())
        }
        return networkService.getAccessToken(requestMap).execute()
    }

    fun requestMembershipIdAndGrade(userId: String): Response<Any> {
        val networkService: NetworkServiceGson = provideRetrofit(commonInterceptor).create(NetworkServiceGson::class.java)
        return networkService.getUserMembershipId(userId).execute()
    }

    fun requestMembershipInfo(): Response<Any> {
        val networkService: NetworkServiceGson = provideRetrofit(commonInterceptor).create(
            NetworkServiceGson::class.java)
        return networkService.getUserMembershipInfo().execute()
    }


    //SELF CARE
    //납부 방법 조회
    fun requestSelf04(mdn: String): Response<Any> {
        val networkService: NetworkServiceGson = provideRetrofit(commonInterceptor).create(
            NetworkServiceGson::class.java)
        return networkService.selfcareV1(
            SelfRequestBody(
                header = arrayListOf(SelfRequestInnerHeader(SELF04_CODE, SELF04_HEADER_MSG_TYPE)),
                body = arrayListOf(SelfRequestInnerBody(mdn = mdn))
            )
        ).execute()
    }
}