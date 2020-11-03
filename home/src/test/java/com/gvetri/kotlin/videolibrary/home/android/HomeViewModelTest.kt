package com.gvetri.kotlin.videolibrary.home.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.codingpizza.fake.TEST_FAKE_HOME_USECASE
import com.codingpizza.fake.fakeHomeUseCaseModule
import com.codingpizza.fake.fakeNasaRepositoryModule
import com.codingpizza.fake.fakeNetworkNasaApiModule
import com.codingpizza.homepublic.HomeUseCase
import com.google.common.truth.Truth.assertThat
import com.gvetri.kotlin.videolibrary.core.repository.Event
import com.gvetri.kotlin.videolibrary.core.repository.observeForTesting
import com.gvetri.kotlin.videolibrary.model.error.NasaError
import com.gvetri.testing.factory.generateNasaSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest : KoinTest {

    @get:Rule
    val nasaKoinTestModule = KoinTestRule.create {
        modules(
            fakeHomeUseCaseModule,
            fakeNasaRepositoryModule,
            fakeNetworkNasaApiModule,
        )
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeUseCase: HomeUseCase by inject(named(TEST_FAKE_HOME_USECASE))
    private val nasaSearchResult = generateNasaSearchResult()
    private val testDispatcher = TestCoroutineDispatcher()


    @Test
    fun `ViewModel should call the UseCase and update the success liveData when the result is successful`() =
        runBlocking {

            //given
            val viewModel = HomeViewModel(fakeUseCase, testDispatcher)

            //when
            viewModel.retrieveNasaCollection()

            //then
            val expected = nasaSearchResult


            viewModel.nasaLiveData.observeForTesting {
                assertThat(viewModel.nasaLiveData.value).isEqualTo(expected)
            }



        }

    @Test
    fun `ViewModel should call the usecase and update the errorLiveData when the result is an error`() =
        runBlocking {
            //given
            val viewModel = HomeViewModel(fakeUseCase, testDispatcher)

            //when
            viewModel.retrieveNasaCollection()

            //then
            val expected = NasaError(500, "ServerError2")

            viewModel.errorLiveData.observeForever { error ->
                assertThat(error.getContentIfNotHandled()).isEqualTo(Event(Unit))
            }
        }
}