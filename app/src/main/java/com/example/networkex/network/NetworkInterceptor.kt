package com.example.networkex.network

import com.example.networkex.view.vm.SharedViewModelGson.Companion.ACCESS_TOKEN
import com.example.networkex.const.HeaderConst.ACCESS_TOKEN_BASIC
import com.example.networkex.const.HeaderConst.ASP_NAME
import com.example.networkex.const.HeaderConst.ASP_VALUE
import com.example.networkex.const.HeaderConst.AUTHORIZATION_NAME
import com.example.networkex.const.HeaderConst.CLIENT_NAME
import com.example.networkex.const.HeaderConst.CLIENT_SECRET_NAME
import com.example.networkex.const.HeaderConst.CLIENT_SECRET_VALUE
import com.example.networkex.const.HeaderConst.CLIENT_VALUE
import com.example.networkex.const.HeaderConst.TRANSACTION_ID
import com.example.networkex.util.NetworkUtil.getTransactionId
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object NetworkInterceptor {
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().run {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(interceptor)
            build()
        }
    }

    val konaCardInterceptor = Interceptor { chain ->
        with(chain) {
            val request = request().newBuilder()
                .addHeader(AUTHORIZATION_NAME, ACCESS_TOKEN!!)
                .addHeader(ASP_NAME, ASP_VALUE)
                .addHeader(TRANSACTION_ID, getTransactionId())
                .build()
            proceed(request)
        }
    }

    val misAuthInterceptor = Interceptor { chain ->
        with(chain) {
            val request = request().newBuilder()
                .addHeader(AUTHORIZATION_NAME, ACCESS_TOKEN_BASIC)
                .build()
            proceed(request)
        }
    }

    //selfCare, mis
    val commonInterceptor = Interceptor { chain ->
        with(chain) {
            val request = request().newBuilder()
                .addHeader(AUTHORIZATION_NAME, ACCESS_TOKEN!!) //TODO
                .addHeader(CLIENT_NAME, CLIENT_VALUE)
                .addHeader(CLIENT_SECRET_NAME, CLIENT_SECRET_VALUE)
                .build()
            proceed(request)
        }
    }
}