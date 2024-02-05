package com.assignment.catexplorer.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.assignment.catexplorer.data.local.CatsDatabase
import com.assignment.catexplorer.data.mappers.domain.toCatBreedEntity
import com.assignment.catexplorer.data.remote.CatsRemoteMediator
import com.assignment.catexplorer.data.remote.CatsService
import com.assignment.catexplorer.domain.CatsRepository
import com.assignment.catexplorer.domain.model.CatBreedEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

import javax.inject.Inject


class CatsRepositoryImpl @Inject constructor(
    private val catsDatabase: CatsDatabase,
    private val catsService: CatsService,
) : CatsRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getCatBreedsFlow(): Flow<PagingData<CatBreedEntity>> {
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

    override suspend fun getCatBreedDetail(id: String): CatBreedEntity {
        return withContext(Dispatchers.IO) {
            catsDatabase.dao.getCatBreedDetail(id = id).toCatBreedEntity()
        }
    }
}
