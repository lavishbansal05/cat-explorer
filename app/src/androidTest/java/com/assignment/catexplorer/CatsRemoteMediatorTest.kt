package com.assignment.catexplorer

import FakeCatsService
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.assignment.catexplorer.data.local.CatBreedDBEntity
import com.assignment.catexplorer.data.local.CatsDatabase
import com.assignment.catexplorer.data.remote.CatsRemoteMediator
import com.assignment.catexplorer.data.remote.CatsService
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)

class CatsRemoteMediatorTest {

    private lateinit var db: CatsDatabase
    private lateinit var catsService: CatsService
    private lateinit var remoteMediator: CatsRemoteMediator

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context, CatsDatabase::class.java
        ).build()

    }

    @Test
    fun testMediatorSuccessWithEndPageAsFalse() = runTest {
        catsService = FakeCatsService(isPageLimitCrossed = false)
        remoteMediator = CatsRemoteMediator(
            catsDatabase = db,
            catsService = catsService
        )
        val pagingState = PagingState<Int, CatBreedDBEntity>(
            listOf(), null, PagingConfig(10), 10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assert(result is RemoteMediator.MediatorResult.Success)
        assert((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached.not())
    }

    @Test
    fun testMediatorSuccessWithEndPageAsTrue() = runTest {
        catsService = FakeCatsService(isPageLimitCrossed = true)
        remoteMediator = CatsRemoteMediator(
            catsDatabase = db,
            catsService = catsService
        )
        val pagingState = PagingState<Int, CatBreedDBEntity>(
            listOf(), null, PagingConfig(10), 10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assert(result is RemoteMediator.MediatorResult.Success)
        assert((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun whenTotalItemsCountIsNullReturnNull() {
        catsService = FakeCatsService(true)
        remoteMediator = CatsRemoteMediator(
            catsDatabase = db,
            catsService = catsService
        )
        // Given
        val totalItemsCount: Int? = null
        val nextPage: Int? = 2
        val PAGE_LIMIT = 10

        // When
        val result = remoteMediator.isTotalItemCountExceeded(
            totalItemsCount = totalItemsCount,
            nextPage = nextPage,
            pageLimit = PAGE_LIMIT
        )

        // Then
        assert(null == result)
    }

    @Test
    fun whenTotalItemsCountIsLessThanNextPageMultipliedByPageLimitReturnFalse() {
        catsService = FakeCatsService(isPageLimitCrossed = true)
        remoteMediator = CatsRemoteMediator(
            catsDatabase = db,
            catsService = catsService
        )
        // Given
        val totalItemsCount = 20
        val nextPage = 1
        val PAGE_LIMIT = 10

        // When
        val result = remoteMediator.isTotalItemCountExceeded(
            totalItemsCount = totalItemsCount,
            nextPage = nextPage,
            pageLimit = PAGE_LIMIT
        )

        // Then
        assert(false == result)
    }

    @Test
    fun whenTotalItemsCountIsLessThanNextPageMultipliedByPageLimitReturnTrue() {
        catsService = FakeCatsService(isPageLimitCrossed = true)
        remoteMediator = CatsRemoteMediator(
            catsDatabase = db,
            catsService = catsService
        )

        // Given
        val totalItemsCount = 20
        val nextPage = 3
        val PAGE_LIMIT = 10

        // When
        val result = remoteMediator.isTotalItemCountExceeded(
            totalItemsCount = totalItemsCount,
            nextPage = nextPage,
            pageLimit = PAGE_LIMIT
        )

        // Then
        assert(true == result)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        runTest {
            db.dao.clearCats()
            db.close()
        }
    }
}