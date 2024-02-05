package com.assignment.catexplorer.data.di

import android.content.Context
import androidx.room.Room
import com.assignment.catexplorer.data.local.CatsDatabase
import com.assignment.catexplorer.data.remote.CatsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CatsDataModule constructor(private val context: Context) {
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

}