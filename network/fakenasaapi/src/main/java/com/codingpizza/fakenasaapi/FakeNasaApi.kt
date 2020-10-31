package com.codingpizza.fakenasaapi

import com.codingpizza.apimodel.NasaSearchApiModel
import com.codingpizza.nasaapi.NasaApi
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls

class FakeNasaApi(
    private val behaviorDelegate: BehaviorDelegate<NasaApi>,
    var retrieveNasaCollectionResponse: Response<NasaSearchApiModel?>
) : NasaApi {

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