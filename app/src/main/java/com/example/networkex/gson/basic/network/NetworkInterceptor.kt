package com.example.networkex.gson.basic.network

import com.example.networkex.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

object NetworkInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}