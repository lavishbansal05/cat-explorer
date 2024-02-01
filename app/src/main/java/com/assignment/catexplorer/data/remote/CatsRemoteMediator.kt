package com.assignment.catexplorer.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.assignment.catexplorer.data.local.CatBreedEntity
import com.assignment.catexplorer.data.local.CatsDatabase
import com.assignment.catexplorer.data.mappers.toCatBreedEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CatsRemoteMediator(
    private val catsDatabase: CatsDatabase,
    private val catsService: CatsService,
) : RemoteMediator<Int, CatBreedEntity>() {

    private var nextPage: Int? = 0

    companion object {
        const val PAGE_LIMIT = 20
        private const val PAGINATION_CURRENT_PAGE = "pagination-page"
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatBreedEntity>,
    ): MediatorResult {
        Log.d("LBTEST", "load called")
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> nextPage
            }

            return withContext(Dispatchers.IO) {
                val response = catsService.getCatBreeds(
                    page = loadKey ?: 0,
                    limit = PAGE_LIMIT
                )

                nextPage = response.headers().get(PAGINATION_CURRENT_PAGE)?.toInt()?.plus(1) ?: nextPage?.plus(1)
                Log.d("LBTEST", "nextPage: ${nextPage}")
                Log.d("LBTEST", "headers: ${response.headers()}")

                if (response.isSuccessful) {
                    catsDatabase.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            catsDatabase.dao.clearCats()
                        }
                        response.body()?.map { it.toCatBreedEntity() }?.let { catsEntities ->
                            catsDatabase.dao.upsertCats(catsEntities)
                        }

                    }
                    Log.d("LBTEST", "end of pagination reached: ${response.body()?.isEmpty() == true}")
                    MediatorResult.Success(
                        endOfPaginationReached = response.body()?.isEmpty() == true
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
}