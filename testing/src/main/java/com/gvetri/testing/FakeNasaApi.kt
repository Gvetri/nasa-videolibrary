package com.gvetri.testing

import com.codingpizza.apimodel.NasaSearchApiModel
import com.codingpizza.nasaapi.NasaApi
import com.gvetri.testing.factory.FakeNasaSearchFactory
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls

class FakeNasaApi(
    private val behaviorDelegate: BehaviorDelegate<NasaApi>,
    nasaSearchFactory: FakeNasaSearchFactory = FakeNasaSearchFactory
) : NasaApi {
    var retrieveNasaCollectionResponse: Response<NasaSearchApiModel?> =
        Response.success(nasaSearchFactory.obtainNasaSearchModel())

    override suspend fun retrieveNasaCollection(
        query: String,
        type: String?
    ): Response<NasaSearchApiModel> {
        return behaviorDelegate.returning(Calls.response(retrieveNasaCollectionResponse))
            .retrieveNasaCollection(
                query = query,
                type = type
            )
    }
}
