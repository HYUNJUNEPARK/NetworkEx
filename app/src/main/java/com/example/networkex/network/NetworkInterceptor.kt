package com.example.networkex.network

import com.example.networkex.vm.SharedViewModel.Companion.ACCESS_TOKEN
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

object NetworkInterceptor {
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