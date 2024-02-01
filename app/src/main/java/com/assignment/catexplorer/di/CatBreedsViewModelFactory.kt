package com.assignment.catexplorer.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.catexplorer.data.CatsRepository
import com.assignment.catexplorer.viewmodel.CatBreedsViewModel
import javax.inject.Inject

class CatBreedsViewModelFactory @Inject constructor(private val catsRepository: CatsRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatBreedsViewModel(repository = catsRepository) as T
    }
}