package com.gvetri.kotlin.videolibrary.home.android

import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.testing.factory.TESTING_NASA_REPOSITORY
import com.gvetri.testing.factory.testModule
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class HomeUseCaseTest : KoinTest{

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            testModule
        )
    }

    private val fakeNasaRepository : NasaRepository by inject(named(TESTING_NASA_REPOSITORY))

    @Test
    fun `Usecase should call the repository and return a value`() {
        //given
        val homeUseCase = HomeUseCase(fakeNasaRepository)

    }

}