package com.assignment.catexplorer

import android.app.Application
import com.assignment.catexplorer.data.di.CatsDataModule
import com.assignment.catexplorer.di.AppComponent
import com.assignment.catexplorer.di.DaggerAppComponent

class MyApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initialiseDagger()
    }

    private fun initialiseDagger() {
        appComponent = DaggerAppComponent.builder().catsDataModule(CatsDataModule(context = applicationContext))
            .build()
    }


}