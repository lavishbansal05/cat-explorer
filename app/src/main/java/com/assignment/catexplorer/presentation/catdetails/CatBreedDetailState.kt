package com.assignment.catexplorer.presentation.catdetails

import com.assignment.catexplorer.domain.model.CatBreedEntity

sealed class CatBreedDetailState {
    data object Initial: CatBreedDetailState()

    data class Loaded(val catBreedEntity: CatBreedEntity): CatBreedDetailState()

    data class Error(val error: String? = null): CatBreedDetailState()
}
