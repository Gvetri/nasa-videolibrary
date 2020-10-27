package com.gvetri.kotlin.videolibrary.app

import android.app.Application
import com.gvetri.kotlin.videolibrary.home.android.homeModule
import org.koin.core.context.startKoin

class NasaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                homeModule
            )
        }
    }
}
