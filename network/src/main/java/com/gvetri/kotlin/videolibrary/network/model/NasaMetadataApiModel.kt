package com.gvetri.kotlin.videolibrary.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaMetadataApiModel(
    @SerialName("total_hits")
    val totalHits: Int? = null
)
