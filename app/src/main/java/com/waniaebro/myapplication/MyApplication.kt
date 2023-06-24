package com.waniaebro.myapplication

import android.app.Application
import com.waniaebro.core.di.databaseModule
import com.waniaebro.core.di.networkModule
import com.waniaebro.core.di.repositoryModule
import com.waniaebro.myapplication.di.usecaseModule
import com.waniaebro.myapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    networkModule,
                    viewModelModule,
                    usecaseModule
                )
            )
        }
    }
}