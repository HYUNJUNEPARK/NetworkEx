package com.example.networkex.legacy.network

import com.example.networkex.BuildConfig
import com.example.networkex.legacy.view.vm.SharedViewModelGson.Companion.ACCESS_TOKEN
import com.example.networkex.legacy.const.HeaderConst.ACCESS_TOKEN_BASIC
import com.example.networkex.legacy.const.HeaderConst.AUTHORIZATION_NAME
import com.example.networkex.legacy.const.HeaderConst.TRANSACTION_ID
import com.example.networkex.legacy.const.UrlConst
import com.example.networkex.legacy.const.UrlConst.REL_SERVER_URL
import com.example.networkex.legacy.network.gson.NetworkManagerGson
import com.example.networkex.legacy.network.model.gson.MisResponseAuthToken
import com.example.networkex.legacy.util.AppUtil
import com.example.networkex.legacy.util.AppUtil.makeBase64
import com.example.networkex.legacy.util.AppUtil.makeSHA256AndBase64
import com.example.networkex.legacy.util.NetworkUtil.getTransactionId
import com.example.networkex.legacy.view.MainActivity
import com.example.networkex.legacy.view.MainActivity.Companion.TEST_USER_ID
import com.example.networkex.legacy.view.MainActivity.Companion.TEST_USER_PW
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit

object NetworkInterceptor {
    private val gson = Gson()

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
            authenticator(object : Authenticator {
                override fun authenticate(route: Route?, response: Response): Request? {
                    if (response.code == 401) {
                        Timber.d("401")

                        val userId = makeBase64(TEST_USER_ID)
                        val pw = makeSHA256AndBase64(TEST_USER_PW)

                        val _response = NetworkManagerGson().requestAccessToken(userId, pw, "deviceId1234", "pushToken1234")
                        when(_response.code()) {
                            200 -> {
                                val responseBody = gson.toJson(_response.body())
                                val responseData = gson.fromJson(responseBody, MisResponseAuthToken::class.java)
                                val accessToken = "${responseData.accessToken}"
                                ACCESS_TOKEN = accessToken
                                return getRequest(response, accessToken)

                            }
                            else -> {
                                return null
                            }
                        }
                    }
                    return null
                }
            })
            build()
        }
    }

    private fun getRequest(response: Response, token: String): Request {
        Timber.d("getRequest : $response // token : $token")

        return response.request
            .newBuilder()
            .removeHeader(AUTHORIZATION_NAME)
            .addHeader(AUTHORIZATION_NAME, "Bearer $token")
            .build()
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