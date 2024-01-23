package com.example.networkex.gson.basic.network

import com.example.networkex.gson.basic.model.GitRepository
import com.example.networkex.gson.basic.network.NetworkProvider.provideRetrofit
import retrofit2.Call

class NetworkManager {
    fun requestGithubInfo(login: String): Call<List<GitRepository>> {
        val networkService = provideRetrofit().create(NetworkService::class.java)
        return networkService.listRepos(login)
    }
}