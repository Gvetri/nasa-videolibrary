package com.gvetri.kotlin.videolibrary.network.datasource

import arrow.core.Either
import com.codingpizza.fake.FakeNasaSearchFactory
import com.codingpizza.fake.TEST_FAKE_NASA_API
import com.codingpizza.fake.TEST_NASA_API
import com.codingpizza.fake.fakeNetworkNasaApiModule
import com.codingpizza.fakenasaapi.FakeNasaApi
import com.google.common.truth.Truth.assertThat
import com.gvetri.kotlin.videolibrary.model.NasaData
import com.gvetri.kotlin.videolibrary.model.NasaFileRelation
import com.gvetri.kotlin.videolibrary.model.NasaLinkModel
import com.gvetri.kotlin.videolibrary.model.NasaMediatype
import com.gvetri.kotlin.videolibrary.model.NasaResultItem
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import retrofit2.Response

class NasaNetworkDataSourceTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            fakeNetworkNasaApiModule
        )
    }

    private val contentType = "application/json".toMediaType()

    private val fakeNasaApi: FakeNasaApi by inject(named(TEST_FAKE_NASA_API))

    private val nasaDataModel = NasaData(
        center = "MSFC",
        dateCreated = "2017-02-24T00:00:00Z",
        description = "On February 24, 1987, astronomers in the southern hemisphere saw a supernova in the Large Magellanic Cloud.  This new object was dubbed “Supernova 1987A” and was the brightest stellar explosion seen in over four centuries.  Chandra has observed Supernova 1987A many times and the X-ray data reveal important information about this object.  X-rays from Chandra have shown the expanding blast wave from the original explosion slamming into a ring of material expelled by the star before it exploded.  The latest Chandra data reveal the blast wave has moved beyond the ring into a region that astronomers do not know much about.  These observations can help astronomers learn how supernovas impact their environments and affect future generations of stars and planets.",
        description508 = "Chandra Graphic",
        keywords = listOf("Chandra", "Super Nova", "SN 1987A", "Xray"),
        location = "MSFC",
        mediaType = "video",
        nasaId = "A Quick Look at Supernova 1987A",
        photographer = "Jonathan Clifton",
        title = "A Quick Look at Supernova 1987A"
    )

    private val nasaLinkModelPreview = NasaLinkModel(
        href = "https://images-assets.nasa.gov/video/A Quick Look at Supernova 1987A/A Quick Look at Supernova 1987A~thumb.jpg",
        render = NasaMediatype.from("image"),
        relation = NasaFileRelation.from("preview")
    )

    private val nasaLinkModelCaptions = NasaLinkModel(
        href = "https://images-assets.nasa.gov/video/A Quick Look at Supernova 1987A/A Quick Look at Supernova 1987A.srt",
        render = NasaMediatype.NON_AVAILABLE,
        relation = NasaFileRelation.from("captions")
    )

    private val retrofitErrorMessage = "Response.error()"

//    private val nasaNetworkDataSource = NasaNetworkDataSource(fakeNasaApi)


    @Test
    fun `NasaNetworkDataSource Should return a Successful List of NasaDataModel`() {
        // given
        val nasaNetworkDataSource = NasaNetworkDataSource(fakeNasaApi)
        fakeNasaApi.retrieveNasaCollectionResponse =
            Response.success(FakeNasaSearchFactory.obtainNasaSearchModel())

        runBlocking {
            // when
            val actualResult = nasaNetworkDataSource.retrieveNasaCollection()

            // then
            val expectedNasaSearchResult = NasaSearchResult(
                items =
                listOf(
                    NasaResultItem(
                        dataList = listOf(nasaDataModel),
                        nasaLinkModels = listOf(nasaLinkModelPreview, nasaLinkModelCaptions)
                    )
                )
            )

            val expectedEither = Either.right(expectedNasaSearchResult)

            assertThat(actualResult).isEqualTo(expectedEither)
        }
    }

    @Test
    fun `NasaNetworkDataSource should return an Either left when the Api Response is not successful`() {
        // given
        val nasaNetworkDataSource = NasaNetworkDataSource(fakeNasaApi)
        fakeNasaApi.retrieveNasaCollectionResponse = Response.error(
            404,
            ""
                .toResponseBody(contentType)
        )
        runBlocking {
            // when
            val actualResult = nasaNetworkDataSource.retrieveNasaCollection()
            // then
            val expected = Either.left(NasaError(404, retrofitErrorMessage))
            assertThat(actualResult).isEqualTo(expected)
        }
    }
}
