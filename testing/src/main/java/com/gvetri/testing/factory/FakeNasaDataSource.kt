package com.gvetri.testing.factory

import arrow.core.Either
import com.codingpizza.nasaapi.NasaApi
import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError
import com.gvetri.kotlin.videolibrary.network.datasource.nasaSearchResultMapper
import com.gvetri.testing.FakeNasaApi

class FakeNasaDataSource(
    private val fakeNasaApi: NasaApi = FakeNasaApi(BehaviorDelegateFactory().generate())
) : NasaDataSource {
    override suspend fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult> {
        val serviceResult = fakeNasaApi.retrieveNasaCollection("")
        return if (serviceResult.isSuccessful) {
            Either.right(nasaSearchResultMapper(serviceResult.body()))
        } else {
            Either.left(NasaError(serviceResult.code(), serviceResult.message()))
        }
    }
}
