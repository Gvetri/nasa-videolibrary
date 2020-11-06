package com.codingpizza.apimodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaLinkApiModel(
    @SerialName("href")
    val href: String? = null,
    @SerialName("rel")
    val rel: String? = null,
    @SerialName("render")
    val render: String? = null
)
