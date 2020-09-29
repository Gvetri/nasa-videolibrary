package com.gvetri.kotlin.videolibrary.network.api

import com.gvetri.kotlin.videolibrary.network.model.NasaSearchApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("search")
    fun retrieveNasaCollection(
        @Query("q") query: String,
        @Query("media_type") type: String? = null,
    ): Call<NasaSearchApiModel>
}
