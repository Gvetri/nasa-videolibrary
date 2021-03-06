package com.codingpizza.apimodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaMetadataApiModel(
    @SerialName("total_hits")
    val totalHits: Int? = null
)
