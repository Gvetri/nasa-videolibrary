package com.codingpizza.fake

import com.codingpizza.apimodel.NasaSearchApiModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Suppress("MaxLineLength")
object FakeNasaSearchFactory {
    private val nasaDefaultJson =
        """
        {
            "collection": {
                "href": "https://images-api.nasa.gov/search?q=1987A&media_type=video",
                "metadata": {
                    "total_hits": 1
                },
                "version": "1.0",
                "items": [
                    {
                        "links": [
                            {
                                "render": "image",
                                "href": "https://images-assets.nasa.gov/video/A Quick Look at Supernova 1987A/A Quick Look at Supernova 1987A~thumb.jpg",
                                "rel": "preview"
                            },
                            {
                                "href": "https://images-assets.nasa.gov/video/A Quick Look at Supernova 1987A/A Quick Look at Supernova 1987A.srt",
                                "rel": "captions"
                            }
                        ],
                        "href": "https://images-assets.nasa.gov/video/A Quick Look at Supernova 1987A/collection.json",
                        "data": [
                            {
                                "keywords": [
                                    "Chandra",
                                    "Super Nova",
                                    "SN 1987A",
                                    "Xray"
                                ],
                                "media_type": "video",
                                "center": "MSFC",
                                "description_508": "Chandra Graphic",
                                "date_created": "2017-02-24T00:00:00Z",
                                "description": "On February 24, 1987, astronomers in the southern hemisphere saw a supernova in the Large Magellanic Cloud.  This new object was dubbed “Supernova 1987A” and was the brightest stellar explosion seen in over four centuries.  Chandra has observed Supernova 1987A many times and the X-ray data reveal important information about this object.  X-rays from Chandra have shown the expanding blast wave from the original explosion slamming into a ring of material expelled by the star before it exploded.  The latest Chandra data reveal the blast wave has moved beyond the ring into a region that astronomers do not know much about.  These observations can help astronomers learn how supernovas impact their environments and affect future generations of stars and planets.",
                                "photographer": "Jonathan Clifton",
                                "title": "A Quick Look at Supernova 1987A",
                                "nasa_id": "A Quick Look at Supernova 1987A",
                                "location": "MSFC"
                            }
                        ]
                    }
                ]
            }
        }
        """.trimIndent()

    fun obtainNasaSearchModel(json: String = nasaDefaultJson): NasaSearchApiModel =
        Json.decodeFromString(json)
}
