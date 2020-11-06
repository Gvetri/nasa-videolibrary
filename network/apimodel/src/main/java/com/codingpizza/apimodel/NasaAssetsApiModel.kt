package com.codingpizza.apimodel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaAssetsApiModel(
    @SerialName("collection")
    val collection: NasaAssetsCollectionApiModel? = null
)