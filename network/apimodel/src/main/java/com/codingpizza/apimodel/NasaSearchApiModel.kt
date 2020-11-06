package com.codingpizza.apimodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaSearchApiModel(
    @SerialName("collection")
    val nasaCollectionApiModel: NasaCollectionApiModel? = null
)
