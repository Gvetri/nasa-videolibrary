package com.gvetri.testing

import com.gvetri.kotlin.videolibrary.network.api.NasaApi
import com.gvetri.kotlin.videolibrary.network.model.NasaSearchApiModel
import com.gvetri.testing.factory.FakeNasaSearchFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls

class FakeNasaApi(
    private val behaviorDelegate: BehaviorDelegate<NasaApi>,
    nasaSearchFactory: FakeNasaSearchFactory = FakeNasaSearchFactory
) : NasaApi {
    var retrieveNasaCollectionResponse: Response<NasaSearchApiModel?> =
        Response.success(nasaSearchFactory.obtainNasaSearchModel())

    override fun retrieveNasaCollection(
        query: String,
        type: String?
    ): Call<NasaSearchApiModel> {
        return behaviorDelegate.returning(Calls.response(retrieveNasaCollectionResponse))
            .retrieveNasaCollection(
                query = query,
                type = type
            )
    }
}
