package com.example.networkex.gson.basic.model

import com.google.gson.annotations.SerializedName

data class GitRepository(
    @SerializedName("full_name")
    val fullName: String? = null,
    val name: String? = null
)

