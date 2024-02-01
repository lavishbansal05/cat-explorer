package com.assignment.catexplorer.di

import com.assignment.catexplorer.MainActivity
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun catBreedsViewModelFactory(): CatBreedsViewModelFactory

    fun inject(activity: MainActivity)
}