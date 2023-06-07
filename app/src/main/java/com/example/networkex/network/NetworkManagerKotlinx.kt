package com.example.networkex.network

import com.example.networkex.network.NetworkInterceptor.konaCardInterceptor
import com.example.networkex.network.NetworkProvider.provideRetrofitKotlinxConverter
import com.example.networkex.network.NetworkProvider.provideRetrofitKotlinxConverterSampleApi
import com.example.networkex.network.model.kotlinx.KonaCardRequestBodyUserIdKotlinx
import com.example.networkex.network.model.kotlinx.KonaCardResponseBodyPointInfoKotlinx
import com.example.networkex.network.model.kotlinx.Repo
import retrofit2.Response

class NetworkManagerKotlinx {
    fun requestKotlinxTest(): Response<List<Repo>> {
        val networkService = provideRetrofitKotlinxConverterSampleApi().create(NetworkServiceKotlinx::class.java)
        return networkService.listRepos("ln-12").execute()
    }

    //KONA CARD
    fun requestMembershipPoint(membershipId: String): Response<KonaCardResponseBodyPointInfoKotlinx> {
        val networkService = provideRetrofitKotlinxConverter(konaCardInterceptor).create(NetworkServiceKotlinx::class.java)
        return networkService.pointInfo(KonaCardRequestBodyUserIdKotlinx(membershipId)).execute()
    }
}