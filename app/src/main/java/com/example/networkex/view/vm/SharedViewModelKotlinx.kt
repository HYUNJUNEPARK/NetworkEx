package com.example.networkex.view.vm

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.networkex.network.model.kotlinx.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModelKotlinx: RemoteDataSourceBaseViewModel() {
    fun requestTest() = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        val response = networkManagerKotlinx.requestKotlinxTest()
        when(response.code()) {
            200 -> {
                Log.d("testLog", "requestTest 200 ${response.body()}")
                val responseBody = response.body() as List<Repo>
                Log.d("testLog", "requestTest 200 1212: ${responseBody[0].someWeirdRandomName_12345}")

            }
            400 -> {
                Log.d("testLog", "requestTest 400: $response")
            }
        }
    }
}