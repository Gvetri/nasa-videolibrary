package com.gvetri.testing.factory

import org.koin.core.qualifier.named
import org.koin.dsl.module


const val TESTING_NASA_REPOSITORY = "FakeNasaRepository"


val testModule = module {
    single(named(TESTING_NASA_REPOSITORY)) { provideFakeNasaRepository() }
}

fun provideFakeNasaRepository() {
    return
}