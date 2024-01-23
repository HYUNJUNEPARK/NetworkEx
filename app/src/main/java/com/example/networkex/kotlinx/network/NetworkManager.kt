package com.example.networkex.kotlinx.network

import com.example.networkex.kotlinx.network.NetworkProvider.provideRetrofit
import com.example.networkex.legacy.network.model.kotlinx.Repo
import retrofit2.Call

class NetworkManager {
    fun requestGithubInfo(login: String): Call<List<Repo>> {
        val networkService = provideRetrofit().create(NetworkService::class.java)
        return networkService.listRepos(login)
    }
}