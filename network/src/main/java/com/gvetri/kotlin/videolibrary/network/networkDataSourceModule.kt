package com.gvetri.kotlin.videolibrary.network

import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import com.gvetri.kotlin.videolibrary.network.datasource.NasaNetworkDataSource
import com.gvetri.kotlin.videolibrary.network.module.NASA_API_SERVICE
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val NETWORK_NASA_DATASOURCE = "NetworkNasaDataSource"

val networkDataSourceModule = module {
    single<NasaDataSource>(named(NETWORK_NASA_DATASOURCE)) {
        NasaNetworkDataSource(
            apiService = get(
                named(
                    NASA_API_SERVICE
                )
            ),
        )
    }

}