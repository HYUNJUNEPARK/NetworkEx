package com.example.networkex.gson.basic.network

import com.example.networkex.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.net.SocketTimeoutException

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
        var retryCount = 0

        while (retry && retryCount < 3) {
            try {
                /**
                 * TODO
                 * 200번대 이외의 모든 응답(ex.400, 404, 500 등)에도 retry를 해야할 경우 활성화
                 */
//                if (response != null) {
//                    //To block "cannot make a new request because the previous response is still open: please call response.close()"
//                    response.close()
//                }

                //Retry the request after a delay (you can customize the delay)
                if (retryCount > 0) {
                    Timber.d("Retry Request API sleep Thread 1000ms")
                    Thread.sleep((1000).toLong())
                }

                //Request
                Timber.d("Request : ${request.url} retryCount : $retryCount")
                response = chain.proceed(request)
                Timber.d("httpRetryInterceptor Response : ($response)")

                /**
                 * TODO
                 * 200번대 이외의 모든 응답(ex.400, 404, 500 등)에도 retry를 해야할 경우 활성화
                 */
                //if response code is between 200 and 299, retry is false
                //retry = !response.isSuccessful

                //get a response from server
                retry = false
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Timber.e("NetworkException : ${e.message}")
                    e.printStackTrace()
                }
                when(e) {
                    is SocketTimeoutException -> {
                        Timber.e("SocketTimeoutException : ${request.url}")
                    }
                    else -> {
                        retry = false
                    }
                }
            }
            retryCount++
        }

        Timber.d("httpRetryInterceptor Final Response : $response")
        response ?: chain.proceed(request)
    }
}
