package com.sultandev.evtest.app

import android.app.Application
import com.sultandev.evtest.di.dataModule
import com.sultandev.evtest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.text.SimpleDateFormat
import java.util.*

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        val modules = listOf(dataModule, viewModelModule)
        startKoin {
            androidLogger()

            androidContext(this@App)

            androidFileProperties()

            koin.loadModules(modules)
        }

    }
}