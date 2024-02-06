package com.assignment.catexplorer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.assignment.catexplorer.domain.model.Response
import com.assignment.catexplorer.domain.usecase.GetCatBreedDetailUseCase
import com.assignment.catexplorer.domain.usecase.GetCatBreedsFlowUseCase
import com.assignment.catexplorer.presentation.catdetails.CatBreedDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CatsExplorerViewModel(
    private val getCatBreedDetailUseCase: GetCatBreedDetailUseCase,
    private val getCatBreedsFlowUseCase: GetCatBreedsFlowUseCase,
) : ViewModel() {

    private val _catBreedDetailFlow =
        MutableStateFlow<CatBreedDetailState>(CatBreedDetailState.Initial)

    val catBreedDetailFlow = _catBreedDetailFlow.asStateFlow()

    val catBreedsFlow = getCatBreedsFlowUseCase.invoke().cachedIn(viewModelScope)

    fun fetchCatBreedDetail(id: String) {
        viewModelScope.launch {
            when (val response =
                getCatBreedDetailUseCase.invoke(parameters = GetCatBreedDetailUseCase.Params(catId = id))) {
                is Response.Success -> {
                    _catBreedDetailFlow.emit(CatBreedDetailState.Loaded(response.body))
                }

                is Response.Error -> {
                    _catBreedDetailFlow.emit(CatBreedDetailState.Error(response.error.errorMessage))
                }

                else -> {
                    _catBreedDetailFlow.emit(CatBreedDetailState.Error())
                }
            }
        }
    }
}