package com.example.networkex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkex.MainActivity.Companion.TAG
import com.example.networkex.enums.ResponseState
import com.example.networkex.model.MisResponseAuthToken
import com.example.networkex.model.MisResponseBodyUserId
import com.example.networkex.model.UserIdResult
import com.example.networkex.network.NetworkModule.commonInterceptor
import com.example.networkex.network.NetworkModule.misAuthInterceptor
import com.example.networkex.network.NetworkModule.provideRetrofit
import com.example.networkex.network.NetworkService
import com.example.networkex.util.NetworkUtil
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MainViewModel :  ViewModel() {
    companion object {
        var ACCESS_TOKEN: String? = null
    }
    private val gson = Gson()

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = _isLoading

    private var _accessToken = MutableLiveData<String>()
    val accessToken : LiveData<String> get() = _accessToken

    private var _membershipIdAndGrade = MutableLiveData<UserIdResult>()
    val membershipIdAndGrade : LiveData<UserIdResult> get() = _membershipIdAndGrade

    private var _responseState = MutableLiveData<ResponseState>()
    val responseState : LiveData<ResponseState> get() = _responseState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        endLoading()
        when (throwable) {
            is ConnectException, is UnknownHostException-> {
                _responseState.postValue(ResponseState.BAD_INTERNET)
            }
            is SocketTimeoutException -> {
                _responseState.postValue(ResponseState.TIME_OUT)
            }
            else -> {
                _responseState.postValue(ResponseState.FAIL)
            }
        }
    }

    fun requestAccessToken(userId: String, pwd: String, deviceId: String, pushToken: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
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
        val response = networkService.getAccessToken(requestMap).execute()
        when(response.code()) {
            200 -> {
                endLoading()
                val responseBody = gson.toJson(response.body())
                val responseData = gson.fromJson(responseBody, MisResponseAuthToken::class.java)
                val accessToken = "Bearer ${responseData.accessToken}"
                _accessToken.postValue(accessToken)
                ACCESS_TOKEN = accessToken
            }
            else -> {
                endLoading()
                Log.d(TAG, "requestAccessToken response.code else block")
            }
        }
    }

    /**
     * @param userId 사용자 mdn 또는 Email
     */
    fun requestMembershipIdAndGrade(userId: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
        val networkService: NetworkService = provideRetrofit(commonInterceptor).create(NetworkService::class.java)
        val response = networkService.getUserMembershipId(userId).execute()
        when(response.code()) {
            200 -> {
                endLoading()
                val responseBody = gson.toJson(response.body())
                val responseData = gson.fromJson(responseBody, MisResponseBodyUserId::class.java).result ?: return@launch //TODO 예외처리 필요
                _membershipIdAndGrade.postValue(
                    UserIdResult(
                        grade = responseData.grade,
                        loginId = responseData.loginId,
                        userId = responseData.userId
                    )
                )
            }
            else -> {
                endLoading()
                Log.d(TAG, "requestMembershipIdAndGrade response.code else block")
            }
        }
    }

    private fun startLoading() = viewModelScope.launch {
        _isLoading.value = true
    }

    private fun endLoading() = viewModelScope.launch {
        _isLoading.value = false
    }
}
