package com.gvetri.kotlin.videolibrary.model

enum class NasaFileRelation(val value: String) {
    PREVIEW("preview"),
    CAPTIONS("captions"),
    NON_AVAILABLE("non_available");

    companion object {
        fun from(findValue: String?): NasaFileRelation = values().firstOrNull {
            (it.value == findValue)
        } ?: NON_AVAILABLE
    }
}
