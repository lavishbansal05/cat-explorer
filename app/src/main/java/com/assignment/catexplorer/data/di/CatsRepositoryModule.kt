package com.assignment.catexplorer.data.di

import com.assignment.catexplorer.data.CatsRepositoryImpl
import com.assignment.catexplorer.domain.CatsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class CatsRepositoryModule {
    @Binds
    abstract fun provideCatsRepository(catsRepository: CatsRepositoryImpl): CatsRepository
}