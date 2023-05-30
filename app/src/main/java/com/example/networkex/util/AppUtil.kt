package com.example.networkex.util

import android.content.Context
import android.provider.Settings
import android.util.Base64
import java.security.MessageDigest

object AppUtil {
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun makeBase64(data : String) : String {
        var noNewlineEm = data.encode()
        if(noNewlineEm.length<=4) return noNewlineEm
        if (noNewlineEm.substring(noNewlineEm.length - 4) != "XG4=") return noNewlineEm
        noNewlineEm = noNewlineEm.substring(0..noNewlineEm.length - 5)
        return noNewlineEm
    }

    fun remakeBase64(data : String) : String {
        return data.decode()
    }

    fun makeSHA256AndBase64(data : String) : String {
        return data.sha256().encode()
    }

    private fun String.sha256(): String {
        return hashString(this)
    }

    private fun String.encode(): String {
        return Base64.encodeToString(
            this.toByteArray(charset("UTF-8")),
            Base64.NO_WRAP
        )
    }

    private fun String.decode(): String {
        return Base64.decode(this, Base64.DEFAULT).toString(charset("UTF-8"))
    }

    private fun hashString(input: String): String {
        return MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray())
            .fold("") { str, it ->
                str + "%02x".format(it)
            }
    }
}