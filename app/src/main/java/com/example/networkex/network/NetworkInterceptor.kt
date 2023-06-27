package com.example.networkex.network

import com.example.networkex.BuildConfig
import com.example.networkex.view.vm.SharedViewModelGson.Companion.ACCESS_TOKEN
import com.example.networkex.const.HeaderConst.ACCESS_TOKEN_BASIC
import com.example.networkex.const.HeaderConst.AUTHORIZATION_NAME
import com.example.networkex.const.HeaderConst.TRANSACTION_ID
import com.example.networkex.util.NetworkUtil.getTransactionId
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object NetworkInterceptor {
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().run {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(interceptor).also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }
            build()
        }
    }

    fun konaCardInterceptor(authToken: String): Interceptor {
        return Interceptor { chain ->
            with(chain) {
                val request = request().newBuilder()
                    .addHeader(AUTHORIZATION_NAME, authToken)
                    .addHeader(TRANSACTION_ID, getTransactionId())
                    .build()
                proceed(request)
            }
        }
    }


    val konaCardInterceptor = Interceptor { chain ->
        with(chain) {
            val request = request().newBuilder()
                .addHeader(AUTHORIZATION_NAME, ACCESS_TOKEN!!)
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
                .addHeader(AUTHORIZATION_NAME, ACCESS_TOKEN!!)
                .build()
            proceed(request)
        }
    }
}