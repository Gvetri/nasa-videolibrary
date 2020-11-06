package com.codingpizza.di

import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.kotlin.videolibrary.network.NETWORK_NASA_DATASOURCE
import com.gvetri.kotlin.videolibrary.repository.android.NasaRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val NASA_REPOSITORY = "NasaRepository"

val repositoryModule = module {
    single<NasaRepository>(named(NASA_REPOSITORY)) {
        NasaRepositoryImpl(nasaDataSource = get(named(NETWORK_NASA_DATASOURCE)))
    }
}
