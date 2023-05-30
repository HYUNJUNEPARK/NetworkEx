package com.example.networkex.network

import com.example.networkex.const.UrlConst.REL_SERVER_URL
import com.example.networkex.network.NetworkInterceptor.provideOkHttpClient
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkProvider {
    fun provideRetrofit(interceptor: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(REL_SERVER_URL)
            .client(provideOkHttpClient(interceptor))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}