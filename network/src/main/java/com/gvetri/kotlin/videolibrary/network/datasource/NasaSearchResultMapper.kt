package com.gvetri.kotlin.videolibrary.network.datasource

import com.codingpizza.apimodel.NasaItemApiModel
import com.codingpizza.apimodel.NasaSearchApiModel
import com.gvetri.kotlin.videolibrary.model.NasaData
import com.gvetri.kotlin.videolibrary.model.NasaFileRelation
import com.gvetri.kotlin.videolibrary.model.NasaLinkModel
import com.gvetri.kotlin.videolibrary.model.NasaMediatype
import com.gvetri.kotlin.videolibrary.model.NasaResultItem
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult

fun nasaSearchResultMapper(apiModel: NasaSearchApiModel?): NasaSearchResult {
    return apiModel?.let {
        NasaSearchResult(nasaResultItemMapper(it))
    } ?: NasaSearchResult(emptyList())
}

private fun nasaResultItemMapper(nasaSearchApiModel: NasaSearchApiModel): List<NasaResultItem> {
    val nasaCollectionApiModel = nasaSearchApiModel.nasaCollectionApiModel
    val nasaItems = nasaCollectionApiModel?.items ?: emptyList()

    return nasaItems.mapIndexed { position, _ ->
        NasaResultItem(
            dataList = nasaDataMapper(nasaItems)[position],
            nasaLinkModels = nasaLinkApiMapper(nasaItems)[position],
        )
    }
}

private fun nasaDataMapper(nasaItems: List<NasaItemApiModel>): List<List<NasaData>> {
    val nasaItemsDataList = nasaItems.map { it.dataContent ?: emptyList() }
    return nasaItemsDataList.map {
        it.map { nasaDataApiModel ->
            NasaData(
                center = nasaDataApiModel.center,
                dateCreated = nasaDataApiModel.dateCreated,
                description = nasaDataApiModel.description,
                description508 = nasaDataApiModel.description508,
                keywords = nasaDataApiModel.keywords,
                location = nasaDataApiModel.location,
                mediaType = nasaDataApiModel.mediaType,
                nasaId = nasaDataApiModel.nasaId,
                photographer = nasaDataApiModel.photographer,
                title = nasaDataApiModel.title
            )
        }
    }
}

private fun nasaLinkApiMapper(nasaItems: List<NasaItemApiModel>): List<List<NasaLinkModel>> {
    val nasaItemsLink = nasaItems.map { it.nasaLinkApiModels ?: emptyList() }
    return nasaItemsLink.map {
        it.map { nasaLinkApiModel ->
            NasaLinkModel(
                href = nasaLinkApiModel.href,
                render = NasaMediatype.from(
                    nasaLinkApiModel.render
                ),
                relation = NasaFileRelation.from(
                    nasaLinkApiModel.rel
                )
            )
        }
    }
}

