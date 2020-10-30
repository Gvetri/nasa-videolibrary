package com.codingpizza.di

import com.gvetri.kotlin.videolibrary.network.NETWORK_NASA_DATASOURCE
import com.gvetri.kotlin.videolibrary.repository.android.NasaRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module


const val NASA_REPOSITORY = "NasaRepository"

val repositoryModule = module {
    single(named(NASA_REPOSITORY)) {
        NasaRepository(nasaDataSource = get(named(NETWORK_NASA_DATASOURCE)))
    }
}

