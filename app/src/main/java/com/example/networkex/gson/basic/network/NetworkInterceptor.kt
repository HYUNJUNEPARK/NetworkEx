package com.example.networkex.gson.basic.network

import okhttp3.logging.HttpLoggingInterceptor

object NetworkInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}