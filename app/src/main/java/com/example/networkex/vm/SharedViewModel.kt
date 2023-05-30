package com.example.networkex.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.networkex.enums.ResponseState
import com.example.networkex.model.MisResponseAuthToken
import com.example.networkex.model.MisResponseBodyUserId
import com.example.networkex.model.SelfResponseBody04
import com.example.networkex.model.UserIdResult
import com.konai.mis_apitester.network.model.api.tmp.KonaCardResponseBodyPointInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedViewModel: CoreServerBaseViewModel() {
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

    //API
    fun requestAccessToken(userId: String, pwd: String, deviceId: String, pushToken: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
        val response = networkManager.requestAccessToken(userId, pwd, deviceId, pushToken)
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
                _responseState.postValue(ResponseState.NOT_CODE_200)
            }
        }
    }

    /**
     * @param userId 사용자 mdn 또는 Email
     */
    fun requestMembershipIdAndGrade(userId: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
        val response = networkManager.requestMembershipIdAndGrade(userId)
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
                _responseState.postValue(ResponseState.NOT_CODE_200)
            }
        }
    }

    //멤버십 아이디 -> 잔액
    fun requestCardPointEx1(membershipId: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
        val response = networkManager.requestMembershipPoint(membershipId)
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
                _responseState.postValue(ResponseState.NOT_CODE_200)
            }
        }
    }

    //이메일 -> 멤버십 아이디 -> 잔액
    fun requestCardPointEx2(userId: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
        val membershipId = withContext(Dispatchers.IO) {
            val response = networkManager.requestMembershipIdAndGrade(userId)
            val responseBody = gson.toJson(response.body())
            val responseData = gson.fromJson(responseBody, MisResponseBodyUserId::class.java).result //TODO 예외처리 필요
            responseData!!.userId
        }
        requestCardPointEx1(membershipId!!)
    }

    fun requestSelf04(mdn: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
        val response = networkManager.requestSelf04(mdn)
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
                _responseState.postValue(ResponseState.NOT_CODE_200)
            }
        }
    }
}
