package com.example.networkex.network

import com.example.networkex.BuildConfig
import com.example.networkex.const.UrlConst.DEV_SERVER_URL
import com.example.networkex.const.UrlConst.REL_SERVER_URL
import com.example.networkex.network.NetworkInterceptor.provideOkHttpClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkProvider {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun provideRetrofit(interceptor: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(REL_SERVER_URL)
            .client(provideOkHttpClient(interceptor))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideRetrofitKotlinxConverter(interceptor: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(REL_SERVER_URL)
            .client(provideOkHttpClient(interceptor))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .build()
    }

    fun provideRetrofitKotlinxConverterSampleApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .build()
    }
}