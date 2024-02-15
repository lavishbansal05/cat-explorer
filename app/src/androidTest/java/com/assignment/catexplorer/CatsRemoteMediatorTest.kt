package com.assignment.catexplorer

import FakeCatsService
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(
            context, CatsDatabase::class.java
        ).build()

    }

    @Test
    fun testMediatorSuccessWithEndPageAsFalse() = runTest {
        catsService = FakeCatsService(isEndPageReached = false)

        remoteMediator = CatsRemoteMediator(
            catsDatabase = db,
            catsService = catsService
        )
        val pagingState = PagingState<Int, CatBreedDBEntity>(
            listOf(), null, PagingConfig(10), 0
        )

        val result = remoteMediator.load(LoadType.APPEND, pagingState)


        assert(result is RemoteMediator.MediatorResult.Success)
        assert((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached.not())
    }

    @Test
    fun testMediatorSuccessWithEndPageAsTrue() = runTest {
        catsService = FakeCatsService(isEndPageReached = true)
        remoteMediator = CatsRemoteMediator(
            catsDatabase = db,
            catsService = catsService
        )
        val pagingState = PagingState<Int, CatBreedDBEntity>(
            listOf(), null, PagingConfig(10), 10
        )

        val result = remoteMediator.load(LoadType.APPEND, pagingState)
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
        val nextPage = 2
        val pageLimit = 10

        // When
        val result = remoteMediator.isTotalItemCountExceeded(
            totalItemsCount = totalItemsCount,
            nextPage = nextPage,
            pageLimit = pageLimit
        )

        // Then
        assert(result == null)
    }

    @Test
    fun whenTotalItemsCountIsLessThanNextPageMultipliedByPageLimitReturnFalse() {
        catsService = FakeCatsService(isEndPageReached = true)
        remoteMediator = CatsRemoteMediator(
            catsDatabase = db,
            catsService = catsService
        )
        // Given
        val totalItemsCount = 20
        val nextPage = 1
        val pageLimit = 10

        // When
        val result = remoteMediator.isTotalItemCountExceeded(
            totalItemsCount = totalItemsCount,
            nextPage = nextPage,
            pageLimit = pageLimit
        )

        // Then
        assert(result == false)
    }

    @Test
    fun whenTotalItemsCountIsLessThanNextPageMultipliedByPageLimitReturnTrue() {
        catsService = FakeCatsService(isEndPageReached = true)
        remoteMediator = CatsRemoteMediator(
            catsDatabase = db,
            catsService = catsService
        )

        // Given
        val totalItemsCount = 20
        val nextPage = 3
        val pageLimit = 10

        // When
        val result = remoteMediator.isTotalItemCountExceeded(
            totalItemsCount = totalItemsCount,
            nextPage = nextPage,
            pageLimit = pageLimit
        )

        // Then
        assert(result == true)
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