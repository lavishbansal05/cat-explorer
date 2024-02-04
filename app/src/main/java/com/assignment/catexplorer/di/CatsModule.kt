package com.assignment.catexplorer.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.Room
import com.assignment.catexplorer.data.local.CatsDatabase
import com.assignment.catexplorer.data.mappers.domain.toCatBreedEntity
import com.assignment.catexplorer.data.remote.CatsRemoteMediator
import com.assignment.catexplorer.data.remote.CatsService
import com.assignment.catexplorer.domain.model.CatBreedEntity
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CatsModule constructor(private val context: Context) {
    @Provides
    fun context(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideCatsService(): CatsService {
        return Retrofit.Builder()
            .baseUrl(CatsService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatsService::class.java)
    }

    @Provides
    @Singleton
    fun provideCatsDatabase(context: Context): CatsDatabase {
        return Room.databaseBuilder(
            context,
            CatsDatabase::class.java,
            "cats.db"
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideCatBreedsPagerFlow(
        catsDatabase: CatsDatabase,
        catsService: CatsService,
    ): Flow<PagingData<CatBreedEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = CatsRemoteMediator.PAGE_LIMIT,
                initialLoadSize = CatsRemoteMediator.INITIAL_LOAD_SIZE,
                prefetchDistance = CatsRemoteMediator.PREFETCH_DISTANCE
            ),
            remoteMediator = CatsRemoteMediator(
                catsDatabase = catsDatabase,
                catsService = catsService
            ),
            pagingSourceFactory = {
                catsDatabase.dao.getCatsPagingSource()
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map { it.toCatBreedEntity() }
            }
    }

}