package com.gvetri.kotlin.videolibrary.model

enum class NasaMediatype(val value: String) {
    IMAGE("image"),
    VIDEO("video"),
    NON_AVAILABLE("non_available");

    companion object {
        fun from(findValue: String?): NasaMediatype = values().firstOrNull {
            (it.value == findValue)
        } ?: NON_AVAILABLE
    }
}
