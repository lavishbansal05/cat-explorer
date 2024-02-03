package com.assignment.catexplorer.presentation.catdetails

import com.assignment.catexplorer.data.local.CatBreedEntity

sealed class CatBreedDetailState {

    data object Initial: CatBreedDetailState()

    data class Loaded(val catBreedEntity: CatBreedEntity): CatBreedDetailState()

    data object Error: CatBreedDetailState()

}
