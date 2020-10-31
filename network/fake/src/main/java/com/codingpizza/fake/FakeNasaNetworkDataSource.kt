package com.codingpizza.fake

import arrow.core.Either
import com.codingpizza.apimodel.NasaSearchApiModel
import com.codingpizza.nasaapi.NasaApi
import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class FakeNasaNetworkDataSource(
    private val fakeNasaApi: NasaApi,
    private val nasaSearchMapper: (NasaSearchApiModel?) -> NasaSearchResult
) : NasaDataSource {

    override suspend fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult> {
        val serviceResult = fakeNasaApi.retrieveNasaCollection("")
        return if (serviceResult.isSuccessful) {
            Either.right(nasaSearchMapper(serviceResult.body()))
        } else {
            Either.left(NasaError(serviceResult.code(), serviceResult.message()))
        }
    }
}
