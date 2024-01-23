package com.example.networkex.legacy.network.model.kotlinx

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repo(
    @SerialName("full_name")
    val fullName: String? = null,
    val name: String? = null
)

