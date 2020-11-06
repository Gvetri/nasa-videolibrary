package com.codingpizza.apimodel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaAssetsItemApiModel(
    @SerialName("href")
    val href: String? = null
)