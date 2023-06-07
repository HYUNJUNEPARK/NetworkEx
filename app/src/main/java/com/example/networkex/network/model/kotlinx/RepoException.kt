package com.example.networkex.network.model.kotlinx


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoException(
    @SerialName("documentation_url")
    val documentationUrl: String? = null,
    @SerialName("message")
    val message: String? = null
)