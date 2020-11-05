package com.codingpizza.fake

import com.codingpizza.apimodel.NasaAssetsApiModel
import com.codingpizza.apimodel.NasaSearchApiModel
import com.gvetri.kotlin.videolibrary.model.NasaAssetsResult
import com.gvetri.kotlin.videolibrary.model.NasaData
import com.gvetri.kotlin.videolibrary.model.NasaFileRelation
import com.gvetri.kotlin.videolibrary.model.NasaLinkModel
import com.gvetri.kotlin.videolibrary.model.NasaMediatype
import com.gvetri.kotlin.videolibrary.model.NasaResultItem
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult

fun nasaSearchResultMapper(apiModel: NasaSearchApiModel?): NasaSearchResult {
    return apiModel.let {
        NasaSearchResult(
            listOf(
                NasaResultItem(
                    dataList = it?.nasaCollectionApiModel?.items?.mapIndexed { index, nasaItemApiModel ->
                        NasaData(
                            center = nasaItemApiModel.dataContent?.get(index)?.center,
                            dateCreated = nasaItemApiModel.dataContent?.get(index)?.dateCreated,
                            description = nasaItemApiModel.dataContent?.get(index)?.description,
                            description508 = nasaItemApiModel.dataContent?.get(index)?.description508,
                            keywords = nasaItemApiModel.dataContent?.get(index)?.keywords,
                            location = nasaItemApiModel.dataContent?.get(index)?.location,
                            mediaType = nasaItemApiModel.dataContent?.get(index)?.mediaType,
                            nasaId = nasaItemApiModel.dataContent?.get(index)?.nasaId,
                            photographer = nasaItemApiModel.dataContent?.get(index)?.photographer,
                            title = nasaItemApiModel.dataContent?.get(index)?.title
                        )
                    } ?: emptyList(),
                    nasaLinkModels = it?.nasaCollectionApiModel?.items?.map { nasaItemApiModel ->
                        nasaItemApiModel.nasaLinkApiModels?.map { nasaLinkApiModel ->
                            NasaLinkModel(
                                href = nasaLinkApiModel.href,
                                render = NasaMediatype.from(
                                    nasaLinkApiModel.render
                                ),
                                relation = NasaFileRelation.from(
                                    nasaLinkApiModel.rel
                                )
                            )
                        } ?: emptyList()
                    }?.flatten() ?: emptyList(), null
                )
            )
        )
    }
}

fun nasaAssetsResultMapper(apiModel: List<String?>?): NasaAssetsResult {
    if (apiModel == null) return NasaAssetsResult(emptyList())
    val hrefLinks = apiModel.mapNotNull { link -> link }
    return NasaAssetsResult(hrefLinks)
}