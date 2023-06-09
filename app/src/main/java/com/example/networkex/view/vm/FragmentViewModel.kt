package com.example.networkex.view.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.networkex.enums.ResponseExceptionState
import com.example.networkex.network.model.gson.MisResponseBodyUserId
import com.example.networkex.network.model.gson.UserIdResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentViewModel: RemoteDataSourceBaseViewModel() {
    private var _membershipIdAndGrade = MutableLiveData<UserIdResult>()
    val membershipIdAndGrade : LiveData<UserIdResult> get() = _membershipIdAndGrade

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
}