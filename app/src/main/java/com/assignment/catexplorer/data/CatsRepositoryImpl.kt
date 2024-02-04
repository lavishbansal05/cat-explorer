package com.assignment.catexplorer.data

import androidx.paging.PagingData
import com.assignment.catexplorer.data.local.CatsDatabase
import com.assignment.catexplorer.data.mappers.domain.toCatBreedEntity
import com.assignment.catexplorer.data.remote.CatsService
import com.assignment.catexplorer.domain.CatsRepository
import com.assignment.catexplorer.domain.model.CatBreedEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

import javax.inject.Inject


class CatsRepositoryImpl @Inject constructor(
    private val catsDatabase: CatsDatabase,
    private val catsService: CatsService,
    private val catsPagingDataFlow: Flow<PagingData<CatBreedEntity>>,
): CatsRepository {
    override fun getCatBreedsFlow(): Flow<PagingData<CatBreedEntity>> {
        return catsPagingDataFlow
    }

    override suspend  fun getCatBreedDetail(id: String): CatBreedEntity {
        return withContext(Dispatchers.IO) {
                catsDatabase.dao.getCatBreedDetail(id = id).toCatBreedEntity()
        }
    }
}
