package com.example.networkex.legacy.network.kotlinx

import com.example.networkex.legacy.const.UrlConst.POINT_INFO
import com.example.networkex.legacy.network.model.kotlinx.KonaCardRequestBodyUserIdKotlinx
import com.example.networkex.legacy.network.model.kotlinx.KonaCardResponseBodyPointInfoKotlinx
import com.example.networkex.legacy.network.model.kotlinx.Repo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkServiceKotlinx {
    //KONA CARD
    @POST(POINT_INFO)
    fun pointInfo(
        @Body
        body: KonaCardRequestBodyUserIdKotlinx
    ): Call<KonaCardResponseBodyPointInfoKotlinx>

    @GET("users/{user}/repos")
    fun listRepos(
        @Path("user") user: String
    ): Call<List<Repo>>
}