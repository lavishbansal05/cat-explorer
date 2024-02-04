package com.assignment.catexplorer.domain

import androidx.paging.PagingData
import com.assignment.catexplorer.domain.model.CatBreedEntity
import kotlinx.coroutines.flow.Flow

interface CatsRepository {
    fun getCatBreedsFlow(): Flow<PagingData<CatBreedEntity>>

    suspend  fun getCatBreedDetail(id: String): CatBreedEntity
}