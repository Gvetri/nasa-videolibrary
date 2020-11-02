package com.gvetri.testing.factory

import com.gvetri.kotlin.videolibrary.model.NasaData
import com.gvetri.kotlin.videolibrary.model.NasaFileRelation
import com.gvetri.kotlin.videolibrary.model.NasaLinkModel
import com.gvetri.kotlin.videolibrary.model.NasaMediatype
import com.gvetri.kotlin.videolibrary.model.NasaResultItem
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult

/***
 * Function that generates a default NasaSearchResult
 */
@Suppress("MaxLineLength")
fun generateNasaSearchResult(): NasaSearchResult {
    val nasaDataModel = NasaData(
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

    val nasaLinkModelPreview = NasaLinkModel(
        href = "https://images-assets.nasa.gov/video/A Quick Look at Supernova 1987A/A Quick Look at Supernova 1987A~thumb.jpg",
        render = NasaMediatype.from("image"),
        relation = NasaFileRelation.from("preview")
    )

    val nasaLinkModelCaptions = NasaLinkModel(
        href = "https://images-assets.nasa.gov/video/A Quick Look at Supernova 1987A/A Quick Look at Supernova 1987A.srt",
        render = NasaMediatype.NON_AVAILABLE,
        relation = NasaFileRelation.from("captions")
    )
    return NasaSearchResult(
        items =
            listOf(
                NasaResultItem(
                    dataList = listOf(nasaDataModel),
                    nasaLinkModels = listOf(
                        nasaLinkModelPreview,
                        nasaLinkModelCaptions
                    )
                )
            )
    )
}
