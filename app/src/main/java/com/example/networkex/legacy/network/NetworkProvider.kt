package com.example.networkex.legacy.network

import com.example.networkex.legacy.const.UrlConst.REL_SERVER_URL
import com.example.networkex.legacy.network.NetworkInterceptor.konaCardInterceptor
import com.example.networkex.legacy.network.NetworkInterceptor.provideOkHttpClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
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

    //Token 을 파라미터로 받는 인터페이스
    fun provideRetrofitWithToken(interceptor: Interceptor): Retrofit {
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