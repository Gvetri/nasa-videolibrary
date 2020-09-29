package com.gvetri.kotlin.videolibrary.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaSearchApiModel(
    @SerialName("collection")
    val nasaCollectionApiModel: NasaCollectionApiModel? = null
)
