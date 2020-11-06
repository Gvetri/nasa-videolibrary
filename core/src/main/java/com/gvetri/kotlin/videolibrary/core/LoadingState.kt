package com.gvetri.kotlin.videolibrary.core

sealed class LoadingState {
    object Loading : LoadingState()
    object NotLoading : LoadingState()
}
