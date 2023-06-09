package com.example.networkex.view.vm

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.networkex.network.model.kotlinx.RepoException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class SharedViewModelKotlinx: RemoteDataSourceBaseViewModel() {
    fun requestTest(login: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        val response = networkManagerKotlinx.requestKotlinxTest(login)
        when(response.code()) {
            200 -> {
                Log.d("testLog", "requestTest 200 ${response.body()}")
//                val responseBody = response.body() as List<Repo>
//                Log.d("testLog", "requestTest 200 1212: ${responseBody[0].someWeirdRandomName_12345}")
            }
            else -> {
                //TODO 여기서 중단했음 ...
                Log.d("testLog", "requestTest CODE: ${response.code()}")
                val responseBody = response.errorBody().toString()
                val responseBody2 = response.body()
                //val JsonObject = JSONObject(responseBody)
                val jObjError = JSONObject(response.errorBody()!!.string())
                val tt = jObjError as RepoException
                Log.d("testLog", "requestTest: $responseBody // $jObjError")
            }
        }
    }

    fun requestMembershipPoint(membershipId: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        val response = networkManagerKotlinx.requestMembershipPoint(membershipId)
        when(response.code()) {
            200 -> {
                Log.d("testLog", "response.body() : ${response.body()}")
                //val responseBody = response.body() as KonaCardResponseBodyPointInfoKotlinx
                //Log.d("testLog", "requestTest 200 PointAmount ${responseBody.pointAmount}")
            }
            else -> {
                Log.d("testLog", "requestTest 400: $response")
            }
        }
    }
}