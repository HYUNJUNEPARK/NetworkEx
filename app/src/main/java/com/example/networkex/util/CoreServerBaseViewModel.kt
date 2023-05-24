package com.example.networkex.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.networkex.enums.ResponseState
import com.example.networkex.network.NetworkManager
import com.google.gson.Gson

abstract class CoreServerBaseViewModel: ViewModel() {
    protected val networkManager = NetworkManager()
    protected val gson = Gson()

    protected var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = _isLoading

    protected var _responseState = MutableLiveData<ResponseState>()
    val responseState : LiveData<ResponseState> get() = _responseState
}