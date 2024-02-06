package com.assignment.catexplorer.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.assignment.catexplorer.data.local.CatBreedDBEntity
import com.assignment.catexplorer.data.local.CatsDatabase
import com.assignment.catexplorer.data.mappers.db.toCatBreedDBEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class CatsRemoteMediator(
    private val catsDatabase: CatsDatabase,
    private val catsService: CatsService,
) : RemoteMediator<Int, CatBreedDBEntity>() {

    private var nextPage: Int? = 0
    private var totalItemsCount: Int? =null

    companion object {
        const val PAGE_LIMIT = 10
        const val INITIAL_LOAD_SIZE = 20
        const val PREFETCH_DISTANCE = 2
        private const val PAGINATION_CURRENT_PAGE = "pagination-page"
        private const val TOTAL_ITEMS_COUNT = "pagination-count"
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        return withContext(Dispatchers.IO) {
            if (System.currentTimeMillis() - (catsDatabase.dao.getLastModifiedTS()
                    ?: 0) <= cacheTimeout
            ) {
                Log.d("LBTEST", "initialise called 1")
                // Cached data is up-to-date, so there is no need to re-fetch
                // from the network.
                InitializeAction.SKIP_INITIAL_REFRESH
            } else {
                Log.d("LBTEST", "initialise called 2")
                // Need to refresh cached data from network; returning
                // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
                // APPEND and PREPEND from running until REFRESH succeeds.
                InitializeAction.LAUNCH_INITIAL_REFRESH
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatBreedDBEntity>,
    ): MediatorResult {
        Log.d("LBTEST", "load called:: loadtype::${loadType}")
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> nextPage
            }

            if(isTotalItemCountExceeded() == true) {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }

            return withContext(Dispatchers.IO) {
                val response = catsService.getCatBreeds(
                    page = loadKey ?: 0,
                    limit = PAGE_LIMIT
                )

                nextPage = response.headers().get(PAGINATION_CURRENT_PAGE)?.toInt()?.plus(1)
                    ?: nextPage?.plus(1)
                totalItemsCount = response.headers().get(TOTAL_ITEMS_COUNT)?.toInt()

                Log.d("LBTEST", "nextPage: ${nextPage}")
                Log.d("LBTEST", "headers: ${response.headers()}")

                if (response.isSuccessful) {
                    catsDatabase.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            catsDatabase.dao.clearCats()
                        }
                        response.body()?.map {
                            it.toCatBreedDBEntity().apply { modifiedAt = System.currentTimeMillis() }
                        }?.let { catsEntities ->
                            catsDatabase.dao.upsertCats(catsEntities)
                        }

                    }
                    Log.d(
                        "LBTEST",
                        "end of pagination reached: ${response.body()?.isEmpty() == true}"
                    )
                    MediatorResult.Success(
                        endOfPaginationReached = isTotalItemCountExceeded() ?: (response.body()?.isEmpty() == true)
                    )
                } else {
                    MediatorResult.Error(Throwable(response.errorBody().toString()))
                }
            }


        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private fun isTotalItemCountExceeded(): Boolean? {
        return totalItemsCount?.let { totalItems ->
            (nextPage ?: 0) * PAGE_LIMIT > totalItems
        }.also {
            Log.d("LBTEST", "isTotalItemCountExceeded ${it}")
        }
    }
}