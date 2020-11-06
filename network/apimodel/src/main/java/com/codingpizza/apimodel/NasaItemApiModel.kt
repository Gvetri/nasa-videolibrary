package com.codingpizza.apimodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaItemApiModel(
    @SerialName("data")
    val dataContent: List<NasaDataApiModel>? = null,
    @SerialName("href")
    val href: String? = null,
    @SerialName("links")
    val nasaLinkApiModels: List<NasaLinkApiModel>? = null
)
