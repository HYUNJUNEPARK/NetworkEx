package com.example.networkex.kotlinx.network

import com.example.networkex.legacy.network.model.kotlinx.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {
    @GET("users/{user}/repos")
    fun listRepos(
        @Path("user") user: String
    ): Call<List<Repo>>
}