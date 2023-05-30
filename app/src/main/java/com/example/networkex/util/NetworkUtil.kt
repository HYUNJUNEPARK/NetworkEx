package com.example.networkex.util

import java.text.SimpleDateFormat
import java.util.*

object NetworkUtil {
    /**
     * 코나카드 API header X-KM-Correlation-Id 에서 필요한 transactionId 를 반환한다.
     * @return X-KM-Correlation-Id(7자리, Hex String)
     */
    fun getTransactionId(): String {
        val allowedCharacters = "0123456789qwertyuiopasdfghjklzxcvbnm"
        val currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        val currentDateAndTime: String = sdf.format(currentTime)
        val sizeOfRandomString = 7
        val random = Random()
        val hexStringBuilder = StringBuilder(sizeOfRandomString)
        for (i in 0 until sizeOfRandomString){
            hexStringBuilder.append(allowedCharacters[random.nextInt(allowedCharacters.length)])
        }
        return "$currentDateAndTime-$hexStringBuilder"
    }
}