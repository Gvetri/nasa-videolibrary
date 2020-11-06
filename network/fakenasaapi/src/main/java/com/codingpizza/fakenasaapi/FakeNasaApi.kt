package com.codingpizza.fakenasaapi

import com.codingpizza.apimodel.NasaSearchApiModel
import com.codingpizza.nasaapi.NasaApi
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls

val defaultLinkResponse = listOf(
    "http://images-assets.nasa.gov/video/A Quick Look at Supernova 1987A/A Quick Look at Supernova 1987A~large.jpg",
    "http://images-assets.nasa.gov/video/A Quick Look at Supernova 1987A/A Quick Look at Supernova 1987A~preview_thumb_00001.png",
    "http://images-assets.nasa.gov/video/A Quick Look at Supernova 1987A/A Quick Look at Supernova 1987A~preview_thumb_00002.png",
    "http://images-assets.nasa.gov/video/A Quick Look at Supernova 1987A/A Quick Look at Supernova 1987A~mobile.mp4"
)

class FakeNasaApi(
    private val behaviorDelegate: BehaviorDelegate<NasaApi>,
    var retrieveNasaCollectionResponse: Response<NasaSearchApiModel?>,
    var nasaAssetsLinksresponse: Response<List<String?>?> = Response.success(defaultLinkResponse)
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

    override suspend fun retrieveAssetsList(href: String): Response<List<String?>?> {
        return behaviorDelegate.returning(
            Calls.response(nasaAssetsLinksresponse)
        ).retrieveAssetsList(href)
    }
}
