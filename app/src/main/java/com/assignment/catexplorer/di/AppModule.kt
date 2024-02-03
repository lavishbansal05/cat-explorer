package com.assignment.catexplorer.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.Room
import com.assignment.catexplorer.data.local.CatBreedEntity
import com.assignment.catexplorer.data.local.CatsDatabase
import com.assignment.catexplorer.data.remote.CatsRemoteMediator
import com.assignment.catexplorer.data.remote.CatsService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
public class AppModule constructor(private val context: Context) {
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
                initialLoadSize = CatsRemoteMediator.PAGE_LIMIT
            ),
            remoteMediator = CatsRemoteMediator(
                catsDatabase = catsDatabase,
                catsService = catsService
            ),
            pagingSourceFactory = {
                catsDatabase.dao.getCatsPagingSource()
            }
        ).flow
    }
}