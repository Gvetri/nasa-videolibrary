package com.gvetri.kotlin.videolibrary.app

import android.app.Application
import com.codingpizza.di.repositoryModule
import com.gvetri.kotlin.videolibrary.core.coreModule
import com.gvetri.kotlin.videolibrary.home.android.homeModule
import com.gvetri.kotlin.videolibrary.network.module.networkModule
import com.gvetri.kotlin.videolibrary.network.networkDataSourceModule
import org.koin.core.context.startKoin

class NasaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                coreModule,
                repositoryModule,
                homeModule,
                networkDataSourceModule,
                networkModule
            )
        }
    }
}
