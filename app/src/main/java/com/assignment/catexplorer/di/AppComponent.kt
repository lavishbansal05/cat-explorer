package com.assignment.catexplorer.di

import com.assignment.catexplorer.data.di.CatsDataModule
import com.assignment.catexplorer.data.di.CatsRepositoryModule
import com.assignment.catexplorer.presentation.CatsExplorerActivity
import com.assignment.catexplorer.presentation.di.vmfactory.CatExplorerViewModelFactory
import dagger.Component
import javax.inject.Singleton


@Component(modules = [CatsDataModule::class, CatsRepositoryModule::class])
@Singleton
interface AppComponent {
    fun catBreedsViewModelFactory(): CatExplorerViewModelFactory
    fun inject(activity: CatsExplorerActivity)
}