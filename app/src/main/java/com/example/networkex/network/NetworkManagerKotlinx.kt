package com.example.networkex.network

import com.example.networkex.network.NetworkProvider.provideRetrofitKotlinxConverter
import com.example.networkex.network.model.kotlinx.Repo
import retrofit2.Response

class NetworkManagerKotlinx {
    fun requestKotlinxTest(): Response<List<Repo>> {
        val networkService = provideRetrofitKotlinxConverter().create(NetworkService::class.java)
        return networkService.listRepos("ln-12").execute()
    }
}