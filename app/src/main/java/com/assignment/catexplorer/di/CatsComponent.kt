package com.assignment.catexplorer.di

import com.assignment.catexplorer.presentation.CatsExplorerActivity
import dagger.Component
import javax.inject.Singleton


@Component(modules = [CatsModule::class, CatsRepositoryModule::class])
@Singleton
interface CatsComponent {
    fun catBreedsViewModelFactory(): CatBreedsViewModelFactory
    fun inject(activity: CatsExplorerActivity)
}