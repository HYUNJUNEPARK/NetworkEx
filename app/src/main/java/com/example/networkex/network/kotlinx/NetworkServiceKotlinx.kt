package com.example.networkex.network.kotlinx

import com.example.networkex.const.UrlConst.POINT_INFO
import com.example.networkex.network.model.kotlinx.KonaCardRequestBodyUserIdKotlinx
import com.example.networkex.network.model.kotlinx.KonaCardResponseBodyPointInfoKotlinx
import com.example.networkex.network.model.kotlinx.Repo
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