package com.codingpizza.apimodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaCollectionApiModel(
    @SerialName("href")
    val href: String? = null,
    @SerialName("items")
    val items: List<NasaItemApiModel>? = null,
    @SerialName("metadata")
    val nasaMetadataApiModel: NasaMetadataApiModel? = null,
    @SerialName("version")
    val version: String? = null
)
