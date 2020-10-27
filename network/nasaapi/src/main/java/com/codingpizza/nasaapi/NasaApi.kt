package com.codingpizza.nasaapi

import com.codingpizza.apimodel.NasaSearchApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("search")
    suspend fun retrieveNasaCollection(
        @Query("q") query: String,
        @Query("media_type") type: String? = null,
    ): Response<NasaSearchApiModel>
}
