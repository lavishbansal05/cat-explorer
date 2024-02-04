package com.assignment.catexplorer.domain.usecase

import androidx.paging.PagingData
import com.assignment.catexplorer.domain.CatsRepository
import com.assignment.catexplorer.domain.model.CatBreedEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatBreedsFlowUseCase @Inject constructor(
    private val catsRepository: CatsRepository,
) {

    fun invoke(): Flow<PagingData<CatBreedEntity>> {
        return catsRepository.getCatBreedsFlow()
    }

}