package com.gvetri.kotlin.videolibrary.network.datasource

import arrow.core.Either
import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import com.gvetri.kotlin.videolibrary.model.NasaData
import com.gvetri.kotlin.videolibrary.model.NasaFileRelation
import com.gvetri.kotlin.videolibrary.model.NasaLinkModel
import com.gvetri.kotlin.videolibrary.model.NasaMediatype
import com.gvetri.kotlin.videolibrary.model.NasaResultItem
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError
import com.gvetri.kotlin.videolibrary.network.api.NasaApi
import com.gvetri.kotlin.videolibrary.network.model.NasaSearchApiModel

class NasaNetworkDataSource(
    private val apiService: NasaApi,
    private val nasaSearchMapper: (NasaSearchApiModel?) -> NasaSearchResult =
        {
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
                        }?.flatten() ?: emptyList()
                    )
                )
            )
        }
) : NasaDataSource {
    override fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult> {
        val serviceCall = apiService.retrieveNasaCollection("")
        val serviceResult = serviceCall.execute()
        return if (serviceResult.isSuccessful) {
            Either.right(nasaSearchMapper(serviceResult.body()))
        } else {
            Either.left(NasaError(serviceResult.code(), serviceResult.message()))
        }
    }
}
