package com.codingpizza.apimodel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaAssetsCollectionApiModel(
    @SerialName("href")
    val href: String? = null,
    @SerialName("items")
    val nasaAssetsItemApiModels: List<NasaAssetsItemApiModel>? = null,
    @SerialName("version")
    val version: String? = null
)