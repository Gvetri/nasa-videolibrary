package com.gvetri.kotlin.videolibrary.repository.android

import com.gvetri.kotlin.videolibrary.network.module.NETWORK_NASA_DATASOURCE
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val NASA_REPOSITORY = "NasaRepository"

val nasaRepository = module {
    single(named(NASA_REPOSITORY)) {
        NasaRepository(
            nasaDataSource = get(
                named(
                    NETWORK_NASA_DATASOURCE
                )
            )
        )
    }
}
