package com.example.networkex.network.model.kotlinx

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repo(
    @SerialName("full_name")
    val someWeirdRandomName_12345: String,
    val name: String
)

