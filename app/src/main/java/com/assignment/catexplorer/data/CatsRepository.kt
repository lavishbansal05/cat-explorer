package com.assignment.catexplorer.data

import androidx.paging.Pager
import androidx.paging.PagingData
import com.assignment.catexplorer.data.local.CatBreedEntity
import com.assignment.catexplorer.data.local.CatsDatabase
import com.assignment.catexplorer.data.remote.CatsService
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject


class CatsRepository @Inject constructor(
    private val catsDatabase: CatsDatabase,
    private val catsService: CatsService,
    private val catsPager: Pager<Int, CatBreedEntity>
) {
    fun getCatBreedsFlow(): Flow<PagingData<CatBreedEntity>> {
        return catsPager.flow
    }
}
