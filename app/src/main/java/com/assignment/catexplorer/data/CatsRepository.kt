package com.assignment.catexplorer.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingData
import com.assignment.catexplorer.data.local.CatBreedEntity
import com.assignment.catexplorer.data.local.CatsDatabase
import com.assignment.catexplorer.data.remote.CatsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.Exception

import javax.inject.Inject


class CatsRepository @Inject constructor(
    private val catsDatabase: CatsDatabase,
    private val catsService: CatsService,
    private val catsPagingDataFlow: Flow<PagingData<CatBreedEntity>>,
) {
    fun getCatBreedsFlow(): Flow<PagingData<CatBreedEntity>> {
        return catsPagingDataFlow
    }

    suspend  fun getCatBreedDetail(id: String): CatBreedEntity? {
        return withContext(Dispatchers.IO) {
            try {
                catsDatabase.dao.getCatBreedDetail(id = id)
            } catch(e: Exception) {
                Log.e("CatExplorer", "Error fetching cat details for id ${id}")
                null
            }

        }
    }
}
