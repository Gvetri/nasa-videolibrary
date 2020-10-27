package com.codingpizza.apimodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaDataApiModel(
    @SerialName("center")
    val center: String? = null,
    @SerialName("date_created")
    val dateCreated: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("description_508")
    val description508: String? = null,
    @SerialName("keywords")
    val keywords: List<String>? = null,
    @SerialName("location")
    val location: String? = null,
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("nasa_id")
    val nasaId: String? = null,
    @SerialName("photographer")
    val photographer: String? = null,
    @SerialName("title")
    val title: String? = null
)
