package com.codingpizza.fake

import com.codingpizza.nasarepository.NasaRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val TEST_FAKE_NASA_REPOSITORY = "TestFakeNasaRepository"

val fakeNasaRepositoryModule = module {

    single<NasaRepository>(named(TEST_FAKE_NASA_REPOSITORY)) {
        FakeNasaRepository(get(named(TESTING_NETWORK_NASA_DATASOURCE)))
    }
}
