package com.example.networkex.kotlinx.network

import com.example.networkex.kotlinx.model.GitRepository
import com.example.networkex.kotlinx.network.NetworkProvider.provideRetrofit
import retrofit2.Call

class NetworkManager {
    fun requestGithubInfo(login: String): Call<List<GitRepository>> {
        val networkService = provideRetrofit().create(NetworkService::class.java)
        return networkService.listRepos(login)
    }
}