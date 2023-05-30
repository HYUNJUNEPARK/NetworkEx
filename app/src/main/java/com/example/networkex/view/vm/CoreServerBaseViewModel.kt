package com.example.networkex.view.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkex.enums.ResponseState
import com.example.networkex.network.NetworkManager
import com.example.networkex.view.MainActivity.Companion.TAG
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class CoreServerBaseViewModel: ViewModel() {
    protected val networkManager = NetworkManager()
    protected val gson = Gson()

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = _isLoading

    protected var _responseState = MutableLiveData<ResponseState>()
    val responseState : LiveData<ResponseState> get() = _responseState

    protected fun startLoading() = viewModelScope.launch {
        Log.d(TAG, "startLoading: ")
        _isLoading.value = true
    }

    protected fun endLoading() = viewModelScope.launch {
        Log.d(TAG, "endLoading: ")
        _isLoading.value = false
    }

    protected val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        endLoading()
        when (throwable) {
            is ConnectException, is UnknownHostException -> {
                _responseState.postValue(ResponseState.BAD_INTERNET)
            }
            is SocketTimeoutException -> {
                _responseState.postValue(ResponseState.TIME_OUT)
            }
            else -> { //NullPointException, ClassCastException
                _responseState.postValue(ResponseState.FAIL)
            }
        }
    }
}