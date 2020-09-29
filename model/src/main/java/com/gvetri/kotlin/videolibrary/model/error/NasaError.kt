package com.gvetri.kotlin.videolibrary.model.error

data class NasaError(
    val errorCode: Int,
    val errorMessage: String,
    val errorCause: Throwable? = null
)
