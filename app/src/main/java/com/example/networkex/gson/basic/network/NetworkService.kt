package com.example.networkex.gson.basic.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.networkex.gson.basic.model.GitRepository

interface NetworkService {
    @GET("users/{user}/repos")
    fun listRepos(
        @Path("user") user: String
    ): Call<List<GitRepository>>
}