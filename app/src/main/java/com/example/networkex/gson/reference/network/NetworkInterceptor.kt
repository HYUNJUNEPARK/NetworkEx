package com.example.networkex.gson.reference.network

import com.example.networkex.BuildConfig
import okhttp3.Interceptor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object NetworkInterceptor {
    private val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm".toCharArray()

    /**
     * @param token 토큰 관련 API null 로 전달
     */
    fun getCommonInterceptor(token: String? = null): Interceptor {
        return Interceptor { chain ->
            with(chain) {
                val request = request().newBuilder()
                    .addHeader("SEMO-Transaction-Id", getTransactionId())
                    .addHeader("x-mona-app-version", "Android v${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})")
                    .addHeader("Authorization", token?: "Basic U0VNT19BOlNFTU9fQQ==")
                    .addHeader("content-type", "application/json")
                    .build()
                proceed(request)
            }
        }
    }

    fun getTokenInterceptor(token: String? = null): Interceptor {
        return Interceptor { chain ->
            with(chain) {
                val headerToken = token ?: "Basic bW9uYWFwcDp0aGlzaXNzZWNyZXQ="

                val request = request().newBuilder()
                    .addHeader("X-KM-Correlation-Id", getTransactionId())
                    .addHeader("x-mona-app-version", "Android v${BuildConfig.VERSION_NAME}")
                    .addHeader("Authorization", headerToken)
                    .build()

                proceed(request)
            }
        }
    }

    private fun getTransactionId(): String {
        val stringBuilder = StringBuilder()
        val dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now())
        ALLOWED_CHARACTERS.shuffle()
        stringBuilder.apply {
            append(dateFormat)
            append("-")
            append(String(ALLOWED_CHARACTERS).takeLast(7))
        }
        return stringBuilder.toString()
    }
}