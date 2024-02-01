package com.assignment.catexplorer.viewmodel

import androidx.lifecycle.ViewModel
import com.assignment.catexplorer.data.CatsRepository


class CatBreedsViewModel(private val repository: CatsRepository) : ViewModel() {
    fun getCatsFlow() = repository.getCatBreedsFlow()
}
