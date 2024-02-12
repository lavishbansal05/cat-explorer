package com.assignment.catexplorer

import androidx.paging.PagingData
import com.assignment.catexplorer.domain.model.CatBreedEntity
import com.assignment.catexplorer.domain.model.GenericError
import com.assignment.catexplorer.domain.model.Response
import com.assignment.catexplorer.domain.usecase.GetCatBreedDetailUseCase
import com.assignment.catexplorer.domain.usecase.GetCatBreedsFlowUseCase
import com.assignment.catexplorer.presentation.CatsExplorerViewModel
import com.assignment.catexplorer.presentation.catdetails.CatBreedDetailState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After


@ExperimentalCoroutinesApi
class CatsExplorerViewModelTest {

    private lateinit var viewModel: CatsExplorerViewModel
    private lateinit var getCatBreedDetailUseCase: GetCatBreedDetailUseCase
    private lateinit var getCatBreedsFlowUseCase: GetCatBreedsFlowUseCase
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCatBreedDetailUseCase = mockk()
        getCatBreedsFlowUseCase = mockk(relaxed = true)
        viewModel = CatsExplorerViewModel(getCatBreedDetailUseCase, getCatBreedsFlowUseCase)
    }

    @Test
    fun `fetchCatBreedDetail success`() = runTest {
        // Given
        val catId = "catId"

        val responseSuccess: Response.Success<CatBreedEntity> = Response.Success(
            CatBreedEntity(
                id = "catId",
                name = "catName",
                description = "cat description...",
                imageUrl = "httpd://cdn.cat-xds.in/abc",
                lifeSpan = "6 years",
                origin = "India",
                temperament = "calm",
                childFriendly = 1,
                intelligence = 3,
                affectionLevel = 5
            )
        )

        val pagingData: PagingData<CatBreedEntity> = mockk()
        coEvery { getCatBreedsFlowUseCase.invoke() } returns flowOf(pagingData)

        coEvery {
            getCatBreedDetailUseCase.invoke(parameters = GetCatBreedDetailUseCase.Params(catId = "catId"))
        } returns responseSuccess

        // When
        viewModel.fetchCatBreedDetail(catId)

        // Then
        val state = viewModel.catBreedDetailFlow.first()
        assertEquals(CatBreedDetailState.Loaded(responseSuccess.body), state)

        coVerify {
            getCatBreedDetailUseCase.invoke(
                parameters = GetCatBreedDetailUseCase.Params(
                    catId = "catId"
                )
            )
        }
    }

    @Test
    fun `fetchCatBreedDetail error`() = runTest {
        // Given
        val catId = "catId"

        val responseError: Response.Error<GenericError> =
            Response.Error(GenericError(errorMessage = "Something went wrong"))

        val pagingData: PagingData<CatBreedEntity> = mockk()
        coEvery { getCatBreedsFlowUseCase.invoke() } returns flowOf(pagingData)

        coEvery { getCatBreedDetailUseCase.invoke(any()) } returns responseError

        // When
        viewModel.fetchCatBreedDetail(catId)

        // Then
        val state = viewModel.catBreedDetailFlow.first()
        assertEquals(CatBreedDetailState.Error(responseError.error.errorMessage), state)

        coVerify { getCatBreedDetailUseCase.invoke(any()) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}