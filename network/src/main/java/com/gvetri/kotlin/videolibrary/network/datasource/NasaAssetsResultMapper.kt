package com.gvetri.kotlin.videolibrary.network.datasource

import com.gvetri.kotlin.videolibrary.model.NasaAssetsResult

fun nasaAssetsResultMapper(apiModel: List<String?>?): NasaAssetsResult {
    if (apiModel == null) return NasaAssetsResult(emptyList())
    val hrefLinks = apiModel.mapNotNull { link -> link }
    return NasaAssetsResult(hrefLinks)
}