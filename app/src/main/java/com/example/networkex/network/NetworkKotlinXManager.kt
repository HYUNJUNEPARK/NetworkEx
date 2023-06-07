package com.example.networkex.network

import android.util.Log
import com.example.networkex.network.NetworkProvider.provideRetrofitKotlinXConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Call
import retrofit2.await
import retrofit2.http.GET
import retrofit2.http.Path

class NetworkKotlinXManager {
    fun test() {
        val service = provideRetrofitKotlinXConverter().create(GitHubService::class.java)
        val repos = service.listRepos("ln-12")

        CoroutineScope(Dispatchers.IO).launch {
            val response = repos.execute()
            Log.d("testLog", "onCreate 1234: ${response.code()}")
        }
    }
}

@Serializable
data class Repo(
    @SerialName("full_name")
    val someWeirdRandomName_12345: String,
    val name: String
)

interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>
}