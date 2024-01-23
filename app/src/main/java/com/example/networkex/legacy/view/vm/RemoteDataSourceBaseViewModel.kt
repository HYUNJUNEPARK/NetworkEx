package com.example.networkex.legacy.view.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkex.legacy.enums.ResponseExceptionState
import com.example.networkex.legacy.network.gson.NetworkManagerGson
import com.example.networkex.legacy.network.kotlinx.NetworkManagerKotlinx
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class RemoteDataSourceBaseViewModel: ViewModel() {
    protected val networkManagerGson = NetworkManagerGson()
    protected val networkManagerKotlinx = NetworkManagerKotlinx()
    protected val gson = Gson()

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = _isLoading

    protected var _responseState = MutableLiveData<ResponseExceptionState>()
    val responseState : LiveData<ResponseExceptionState> get() = _responseState

    protected fun startLoading() = viewModelScope.launch {
        _isLoading.value = true
    }

    protected fun endLoading() = viewModelScope.launch {
        _isLoading.value = false
    }

    protected val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        endLoading()
        Timber.e("$throwable")
        when (throwable) {
            is ConnectException, is UnknownHostException -> {
                _responseState.postValue(ResponseExceptionState.BAD_INTERNET)
            }
            is SocketTimeoutException -> {
                _responseState.postValue(ResponseExceptionState.TIME_OUT)
            }
            else -> { //NullPointException, ClassCastException
                throwable.printStackTrace()
                _responseState.postValue(ResponseExceptionState.FAIL)
            }
        }
    }
}