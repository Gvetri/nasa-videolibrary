package com.codingpizza.fake

import arrow.core.Either
import com.codingpizza.apimodel.NasaSearchApiModel
import com.codingpizza.nasaapi.NasaApi
import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import com.gvetri.kotlin.videolibrary.model.NasaAssetsResult
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class FakeNasaNetworkDataSource(
    private val fakeNasaApi: NasaApi,
    private val nasaSearchMapper: (NasaSearchApiModel?) -> NasaSearchResult = ::nasaSearchResultMapper,
    private val nasaAssetsMapper: (List<String?>?) -> NasaAssetsResult = ::nasaAssetsResultMapper
) : NasaDataSource {

    override suspend fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult> {
        val serviceResult = fakeNasaApi.retrieveNasaCollection(
            ""
        )
        return if (serviceResult.isSuccessful) {
            Either.right(nasaSearchMapper(serviceResult.body()))
        } else {
            Either.left(NasaError(serviceResult.code(), serviceResult.message()))
        }
    }

    override suspend fun retrieveVideoUrl(href: String): Either<NasaError, NasaAssetsResult> {
        val serviceResult = fakeNasaApi.retrieveAssetsList(href)
        return if (serviceResult.isSuccessful) {
            Either.right(nasaAssetsMapper(serviceResult.body()))
        } else {
            Either.left(NasaError(serviceResult.code(), serviceResult.message()))
        }
    }
}
