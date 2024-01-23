package com.example.networkex.kotlinx.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitRepository(
    @SerialName("full_name")
    val fullName: String? = null,
    val name: String? = null
)

