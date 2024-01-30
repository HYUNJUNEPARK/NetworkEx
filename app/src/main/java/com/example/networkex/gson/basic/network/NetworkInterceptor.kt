package com.example.networkex.gson.basic.network

import com.example.networkex.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

object NetworkInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    val httpRetryInterceptor = Interceptor { chain ->
        val request = chain.request()
        var response: Response? = null
        var retry = true
        var tryCount = 0

        while (retry && tryCount < 3) {
            try {
                //To block "cannot make a new request because the previous response is still open: please call response.close()"
                response?.close()

                //Retry the request after a delay (you can customize the delay)
                Thread.sleep((3000).toLong())

                //Request
                tryCount++
                Timber.d("Request : ${request.url} TryCount : $tryCount")
                response = chain.proceed(request)
                Timber.d("httpRetryInterceptor Response : ($response)")

                //if response code is between 200 and 299, retry is false
                retry = !response.isSuccessful
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Timber.e("NetworkException : ${e.message}")
                    e.printStackTrace()
                }
            }
        }

        Timber.d("httpRetryInterceptor Final Response : $response")
        response ?: chain.proceed(request)
    }
}
