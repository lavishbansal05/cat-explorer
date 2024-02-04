package com.assignment.catexplorer.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.catexplorer.domain.usecase.GetCatBreedDetailUseCase
import com.assignment.catexplorer.domain.usecase.GetCatBreedsFlowUseCase
import com.assignment.catexplorer.presentation.CatsExplorerViewModel
import javax.inject.Inject

class CatBreedsViewModelFactory @Inject constructor(
    private val getCatBreedDetailUseCase: GetCatBreedDetailUseCase,
    private val getCatBreedsFlowUseCase: GetCatBreedsFlowUseCase,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatsExplorerViewModel(
            getCatBreedDetailUseCase = getCatBreedDetailUseCase,
            getCatBreedsFlowUseCase = getCatBreedsFlowUseCase
        ) as T
    }
}