package com.example.networkex.gson.reference.network

import com.example.networkex.gson.reference.model.ChatNoticeResponse
import com.example.networkex.gson.reference.model.CommonResponse
import com.example.networkex.gson.reference.model.DeviceInfoRequestBody
import com.example.networkex.gson.reference.model.DeviceInfoResponse
import com.example.networkex.gson.reference.model.OAuthTokenResponse
import com.example.networkex.gson.reference.model.TermsDetailResponse
import com.example.networkex.gson.reference.model.TermsListResponse
import com.example.networkex.gson.reference.network.NetworkProvider.provideRetrofit
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class NetworkManager {
    companion object {
        var coreAccessToken: String? = null
        var chatAccessToken: String? = null
    }
    /**
     * 약관 동의 여부 및 약관 목록 조회
     */
    fun requestTermsList(): Call<TermsListResponse> {
        val networkService: NetworkService = provideRetrofit(NetworkInterceptor.getCommonInterceptor(coreAccessToken)).create(NetworkService::class.java)
        return networkService.requestTermsList()
    }

    /**
     * 약관 상세 조회
     */
    fun requestTermsDetail(termsId:Int): Call<TermsDetailResponse> {
        val networkService: NetworkService = provideRetrofit(NetworkInterceptor.getCommonInterceptor(coreAccessToken)).create(NetworkService::class.java)
        return networkService.requestTermsDetail(termsId)
    }

    /**
     * 기기 정보 확인 (Core access token)
     *
     * @param mdn 사용자 mdn
     */
    fun checkDeviceData(mdn: String): Call<DeviceInfoResponse> {
        val networkService: NetworkService = provideRetrofit(NetworkInterceptor.getCommonInterceptor(coreAccessToken)).create(NetworkService::class.java)
        return networkService.checkDeviceData(DeviceInfoRequestBody(mdn))
    }

    /**
     * 유저 삭제 (본인)
     *
     * 현재 token 기능에서 유저 삭제 시 해당 accessToken, refreshToken 을 revoke 하는 로직이 없음.
     * 삭제 된 유저의 토큰으로 요청 시 C_500_001, C_500_002 오류가 발생할 가능성이 매우 높음
     */
    fun requestDeleteUser(): Call<CommonResponse> {
        val networkService: NetworkService = provideRetrofit(NetworkInterceptor.getCommonInterceptor(chatAccessToken)).create(NetworkService::class.java)
        return networkService.requestDeleteUser()
    }

    /**
     * 공지사항 목록 조회
     *
     * @param page 현재 페이지 번호(첫 페이지:0)
     * @param size 페이지 당 데이터 수(Default:20)
     */
    fun requestNotice(page: Int, size: Int = 20): Call<ChatNoticeResponse> {
        val networkService: NetworkService = provideRetrofit(NetworkInterceptor.getCommonInterceptor(
            chatAccessToken
        )).create(NetworkService::class.java)
        return networkService.requestChatNotice(page, size)
    }

    fun refreshToken(
        userId: String,
        refreshToken: String
    ): Call<OAuthTokenResponse> {
        val networkService: NetworkService = provideRetrofit(NetworkInterceptor.getTokenInterceptor()).create(NetworkService::class.java)
        val mediaType = "text/plain"
        val requestMap: HashMap<String, RequestBody> = HashMap()
        requestMap.apply {
            this["grant_type"] = "refresh_token".toRequestBody(mediaType.toMediaTypeOrNull())
            this["username"] = userId.toRequestBody(mediaType.toMediaTypeOrNull())
            this["refresh_token"] = refreshToken.toRequestBody(mediaType.toMediaTypeOrNull())
        }
        return networkService.getAccessToken(requestMap)
    }
}