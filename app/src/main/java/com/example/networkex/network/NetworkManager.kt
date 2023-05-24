package com.example.networkex.network

import com.example.networkex.model.KonaCardRequestBodyUserId
import com.example.networkex.network.NetworkModule.commonInterceptor
import com.example.networkex.network.NetworkModule.konaCardInterceptor
import com.example.networkex.network.NetworkModule.misAuthInterceptor
import com.example.networkex.network.NetworkModule.provideRetrofit
import com.example.networkex.util.NetworkUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class NetworkManager {
    //KONA CARD
    fun requestMembershipPoint(membershipId: String): Response<Any> {
        val networkService: NetworkService = provideRetrofit(konaCardInterceptor).create(NetworkService::class.java)
        return networkService.pointInfo(KonaCardRequestBodyUserId(membershipId)).execute()
    }

    //MIS
    fun requestAccessToken(userId: String, pwd: String, deviceId: String, pushToken: String): Response<Any> {
        val networkService: NetworkService = provideRetrofit(misAuthInterceptor).create(NetworkService::class.java)
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
        val networkService: NetworkService = provideRetrofit(commonInterceptor).create(NetworkService::class.java)
        return networkService.getUserMembershipId(userId).execute()
    }
}