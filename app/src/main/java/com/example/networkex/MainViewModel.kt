package com.example.networkex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkex.MainActivity.Companion.TAG
import com.example.networkex.model.MisResponseAuthToken
import com.example.networkex.network.NetworkModule.misAuthInterceptor
import com.example.networkex.network.NetworkModule.provideRetrofit
import com.example.networkex.network.NetworkService
import com.example.networkex.util.NetworkUtil
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

interface RetrofitCallBack : Callback<Any> {
    override fun onResponse(call: Call<Any>, response: Response<Any>) {}
    override fun onFailure(call: Call<Any>, throwable: Throwable) {

        when(throwable) {
            is ConnectException -> {
                Log.d(TAG, "onFailure: ConnectException")
                //_fetchState.postValue(ResponseState.WRONG_CONNECTION)
            }
            is SocketException -> {
                Log.d(TAG, "onFailure: SocketException")
                //_fetchState.postValue(ResponseState.BAD_INTERNET)
            }
            is HttpException -> {
                Log.d(TAG, "onFailure: HttpException")
                //_fetchState.postValue(ResponseState.PARSE_ERROR)
            }
            is UnknownHostException -> {
                Log.d(TAG, "onFailure: UnknownHostException")
                //_fetchState.postValue(ResponseState.WRONG_CONNECTION)
            }
            else -> { //https://leveloper.tistory.com/179
                Log.d(TAG, "else")
            }
        }
    }
}

class MainViewModel :  ViewModel() {
    companion object {
        var ACCESS_TOKEN: String? = null
    }
    private val gson = Gson()

    private var _accessToken = MutableLiveData<String>()
    val accessToken : LiveData<String> get() = _accessToken



    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = _isLoading

//    protected val _fetchState = MutableLiveData<ResponseState>()
//    val fetchState : LiveData<ResponseState> get() = _fetchState

    fun requestAccessToken(userId: String, pwd: String, deviceId: String, pushToken: String) = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            _isLoading.value = true
        }
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
        networkService.getAccessToken(requestMap).enqueue(object : RetrofitCallBack {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                super.onResponse(call, response)
                viewModelScope.launch {
                    _isLoading.value = false
                }

                when(response.code()) {
                    200 -> {
                        val responseBody = gson.toJson(response.body())
                        val responseData = gson.fromJson(responseBody, MisResponseAuthToken::class.java)
                        val accessToken = "Bearer ${responseData.accessToken}"
                        _accessToken.postValue(accessToken)
                        ACCESS_TOKEN = accessToken
                    }
                }
            }
        })
    }

    /**
     * @param userId 사용자 mdn 또는 Email
     */
//    fun requestMembershipIdAndGrade(userId: String) = viewModelScope.launch(Dispatchers.IO) {
//        withContext(Dispatchers.Main) {
//            _isLoading.value = true
//        }
//        val networkService: NetworkService = provideRetrofit(misAuthInterceptor).create(NetworkService::class.java)
//        networkService.getUserId(userId).enqueue(
//
//        )
//    }
}