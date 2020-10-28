package com.gvetri.kotlin.videolibrary.repository.android

import org.koin.core.qualifier.named
import org.koin.dsl.module

const val NASA_REPOSITORY = "NasaRepository"
const val NETWORK_NASA_DATASOURCE = "NasaRepository"

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
