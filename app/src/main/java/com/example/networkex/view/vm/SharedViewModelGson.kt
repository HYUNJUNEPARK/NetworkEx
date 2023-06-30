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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

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


                val accessToken = "Bearer ${responseData.accessToken}A" //TODO OCCUR 401
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

    //토큰을 파라미터로 넣어준 인터페이스 샘플
    fun requestEx0(membershipId: String, authToken: String?) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        if (authToken == null) {
            Timber.e("AUTH TOKEN NULL")
            return@launch
        }

        startLoading()
        val response = networkManagerGson.requestMembershipPoint(membershipId, authToken)
        when(response.code()) {
            200 -> {
                endLoading()
            }
            else ->{
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

    //[1] 이메일 -> 멤버십 아이디 -> 잔액
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


    //[2] 이메일 -> 멤버십 아이디 -> 잔액(One Scope Example)
    fun requestCardPointEx3(userId: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        startLoading()
        val responseUserId = networkManagerGson.requestMembershipIdAndGrade(userId)
        val membershipId = when(responseUserId.code()) {
            200 -> {
                val responseBody = gson.toJson(responseUserId.body())
                val responseData = gson.fromJson(responseBody, MisResponseBodyUserId::class.java).result
                responseData!!.userId!!
            }
            else -> {
                endLoading()
                throw IllegalArgumentException()
            }
        }

        val responsePoint = networkManagerGson.requestMembershipPoint(membershipId)
        val point = when(responseUserId.code()) {
            200 -> {
                val responseBody = gson.toJson(responsePoint.body())
                val responseData = gson.fromJson(responseBody, KonaCardResponseBodyPointInfo::class.java)
                val pointAmount = if (responseData.pointAmount == null) {
                    "-"
                } else {
                    responseData.pointAmount.toString()
                }
                pointAmount
            }
            else -> {
                endLoading()
                throw  IllegalArgumentException()
            }
        }
        endLoading()
        Log.d(TAG, "RESULT POINT : $point")
    }

    //[3] 이메일 -> 멤버십 아이디 -> 잔액(Split Scope Example)
    fun requestCardPointEx4(userId: String) = viewModelScope.launch(exceptionHandler) {
        val membershipId = getUserId(userId)
        val point = getPoint(membershipId)
        Timber.d("requestCardPointEx4 point : $point")
    }

    suspend fun getUserId(userId: String): String {
        val membershipId = withContext(Dispatchers.IO + exceptionHandler) {
            val responseUserId = networkManagerGson.requestMembershipIdAndGrade(userId)
            when(responseUserId.code()) {
                200 -> {
                    val responseBody = gson.toJson(responseUserId.body())
                    val responseData = gson.fromJson(responseBody, MisResponseBodyUserId::class.java).result
                    responseData!!.userId!!
                }
                else -> {
                    endLoading()
                    throw IllegalArgumentException()
                }
            }
        }
        return membershipId
    }

    suspend fun getPoint(membershipId: String): String {
        val point = withContext(Dispatchers.IO + exceptionHandler) {
            val responsePoint = networkManagerGson.requestMembershipPoint(membershipId)
            when(responsePoint.code()) {
                200 -> {
                    val responseBody = gson.toJson(responsePoint.body())
                    val responseData = gson.fromJson(responseBody, KonaCardResponseBodyPointInfo::class.java)
                    val pointAmount = if (responseData.pointAmount == null) {
                        "-"
                    } else {
                        responseData.pointAmount.toString()
                    }
                    pointAmount
                }
                else -> {
                    endLoading()
                    throw  IllegalArgumentException()
                }
            }
        }
        return point
    }

    //[4] 이메일 -> 멤버십 아이디 -> 잔액(Split Scope Example && Task Async)
    fun requestCardPointEx5(userId: String) = viewModelScope.launch {
        launch {
            val membershipId = getUserId(userId)
            val point = getPoint(membershipId)
            Log.d(TAG, "requestCardPointEx5-1 point : $point")
        }

        launch {
            val membershipId1 = getUserId(userId)
            val point1 = getPoint(membershipId1)
            Log.d(TAG, "requestCardPointEx5-2 point : $point1")
        }

        launch {
            val membershipId2 = getUserId(userId)
            val point2 = getPoint(membershipId2)
            Log.d(TAG, "requestCardPointEx5-3 point : $point2")
        }

        launch {
            val membershipId3 = getUserId(userId)
            val point3 = getPoint(membershipId3)
            Log.d(TAG, "requestCardPointEx5-4 point : $point3")
        }

        launch {
            val membershipId4 = getUserId(userId)
            val point4 = getPoint(membershipId4)
            Log.d(TAG, "requestCardPointEx5-5 point : $point4")
        }
    }


    //[5] 이메일 -> 멤버십 아이디 -> 잔액(Split Scope Example && Task Async) - 마지막에 결과 값이 필요한 경우
    fun requestCardPointEx6(userId: String) = viewModelScope.launch(exceptionHandler) {
        val point = async {
            val membershipId = getUserId(userId)
            val point = getPoint(membershipId)
            Log.d(TAG, "requestCardPointEx5-1 point : $point")
            point
        }

        val point1 = async {
            val membershipId1 = getUserId(userId)
            val point1 = getPoint(membershipId1)
            Log.d(TAG, "requestCardPointEx5-2 point : $point1")
            point1
        }

        val point2 = async {
            val membershipId2 = getUserId(userId)
            val point2 = getPoint(membershipId2)
            Log.d(TAG, "requestCardPointEx5-3 point : $point2")
            point2
        }

        val point3 = async {
            val membershipId3 = getUserId(userId)
            val point3 = getPoint(membershipId3)
            Log.d(TAG, "requestCardPointEx5-4 point : $point3")
            point3
        }

        val point4 = async {
            val membershipId4 = getUserId(userId)
            val point4 = getPoint(membershipId4)
            Log.d(TAG, "requestCardPointEx5-5 point : $point4")
            point4
        }

        Log.d(TAG, "requestCardPointEx6 Result : ${point.await()} / ${point1.await()} / ${point2.await()} / ${point3.await()} / ${point4.await()}")
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
