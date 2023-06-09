package com.example.networkex.view.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.networkex.enums.ResponseExceptionState
import com.example.networkex.network.model.gson.MisResponseAuthToken
import com.example.networkex.network.model.gson.MisResponseBodyUserId
import com.example.networkex.network.model.gson.SelfResponseBody04
import com.example.networkex.network.model.gson.UserIdResult
import com.example.networkex.view.MainActivity.Companion.TAG
import com.example.networkex.network.model.gson.KonaCardResponseBodyPointInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedViewModelGson: RemoteDataSourceBaseViewModel() {
    companion object {
        var ACCESS_TOKEN: String? = null
    }

    //VIEW 에서 Observer 로 결과를 확인하기 위해 LiveData 를 사용. 실제 Request 는 ACCESS_TOKEN 이 사용됨
    private var _accessToken = MutableLiveData<String>()
    val accessToken : LiveData<String> get() = _accessToken

    private var _membershipIdAndGrade = MutableLiveData<UserIdResult>()
    val membershipIdAndGrade : LiveData<UserIdResult> get() = _membershipIdAndGrade

    private var _membershipPoint = MutableLiveData<String>()
    val membershipPoint : LiveData<String> get() = _membershipPoint

    private var _paymentMethod = MutableLiveData<String>()
    val paymentMethod : LiveData<String> get() = _paymentMethod

    private var _sampleData = MutableLiveData<Int>()
    val sampleData : LiveData<Int> get() = _sampleData

    init {
        if (sampleData.value == null ) _sampleData.value = 0
    }

    fun plusSampleData() {
        _sampleData.value = sampleData.value?.plus(1)
        Log.d(TAG, "plusSampleData: ${sampleData.value}")
    }

    fun minusSampleData() {
        _sampleData.value = sampleData.value?.minus(1)
        Log.d(TAG, "minusSampleData: ${sampleData.value}")
    }

    //API
    fun requestAccessToken(userId: String, pwd: String, deviceId: String, pushToken: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
        val response = networkManagerGson.requestAccessToken(userId, pwd, deviceId, pushToken)
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
                _responseState.postValue(ResponseExceptionState.NOT_CODE_200)
            }
        }
    }

    /**
     * @param userId 사용자 mdn 또는 Email
     */
    fun requestMembershipIdAndGrade(userId: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
        val response = networkManagerGson.requestMembershipIdAndGrade(userId)
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
                _responseState.postValue(ResponseExceptionState.NOT_CODE_200)
            }
        }
    }

    //멤버십 아이디 -> 잔액
    fun requestCardPointEx1(membershipId: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
        val response = networkManagerGson.requestMembershipPoint(membershipId)
        when(response.code()) {
            200 -> {
                endLoading()
                val responseBody = gson.toJson(response.body())
                val responseData = gson.fromJson(responseBody, KonaCardResponseBodyPointInfo::class.java)?: return@launch //TODO 예외처리 필요
                val pointAmount = if (responseData.pointAmount == null) {
                    "-"
                } else {
                    responseData.pointAmount.toString()
                }
                _membershipPoint.postValue(pointAmount)
            }
            else ->{
                endLoading()
                _responseState.postValue(ResponseExceptionState.NOT_CODE_200)
            }
        }
    }

    //이메일 -> 멤버십 아이디 -> 잔액
    fun requestCardPointEx2(userId: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
        val membershipId = withContext(Dispatchers.IO) {
            val response = networkManagerGson.requestMembershipIdAndGrade(userId)
            val responseBody = gson.toJson(response.body())
            val responseData = gson.fromJson(responseBody, MisResponseBodyUserId::class.java).result //TODO 예외처리 필요
            responseData!!.userId
        }
        requestCardPointEx1(membershipId!!)
    }

    fun requestSelf04(mdn: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
        val response = networkManagerGson.requestSelf04(mdn)
        when(response.code()) {
            200 -> {
                endLoading()
                val responseBody = gson.toJson(response.body())
                val responseData = gson.fromJson(responseBody, SelfResponseBody04::class.java) ?: return@launch //TODO 예외처리 필요
                val paymentMethod = responseData.body?.first()?.data?.pymMthdNm
                _paymentMethod.postValue(paymentMethod!!)
            }
            else -> {
                endLoading()
                _responseState.postValue(ResponseExceptionState.NOT_CODE_200)
            }
        }
    }
}
