package com.assignment.catexplorer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.collectAsLazyPagingItems
import com.assignment.catexplorer.data.CatsRepository
import com.assignment.catexplorer.data.local.CatBreedEntity
import com.assignment.catexplorer.presentation.catdetails.CatBreedDetailState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception


class CatBreedsViewModel(private val repository: CatsRepository) : ViewModel() {
    val catsPagingDataFlow = repository.getCatBreedsFlow()

    private val _catBreedDetailFlow =
        MutableStateFlow<CatBreedDetailState>(CatBreedDetailState.Initial)
    val catBreedDetailFlow = _catBreedDetailFlow.asStateFlow()

    fun fetchCatBreedDetail(id: String) {
        viewModelScope.launch {
            repository.getCatBreedDetail(id = id)?.let { catEntity ->
                _catBreedDetailFlow.emit(CatBreedDetailState.Loaded(catEntity))
            } ?: _catBreedDetailFlow.emit(CatBreedDetailState.Error)
        }
    }
}