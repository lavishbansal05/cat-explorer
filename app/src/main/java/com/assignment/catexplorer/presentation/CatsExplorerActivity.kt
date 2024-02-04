package com.assignment.catexplorer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.assignment.catexplorer.di.CatsModule
import com.assignment.catexplorer.di.CatBreedsViewModelFactory
import com.assignment.catexplorer.di.DaggerCatsComponent
import com.assignment.catexplorer.presentation.ui.theme.CatExplorerTheme
import javax.inject.Inject

class CatsExplorerActivity : ComponentActivity() {

    @Inject
    lateinit var catBreedsViewModelFactory: CatBreedsViewModelFactory

    private lateinit var catsExplorerViewModel: CatsExplorerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent =
            DaggerCatsComponent.builder().catsModule(CatsModule(context = this.applicationContext))
                .build()
        appComponent.inject(this)
        catsExplorerViewModel =
            ViewModelProvider(this, catBreedsViewModelFactory)[CatsExplorerViewModel::class.java]

        setContent {
            CatExplorerTheme {
                CatsExplorerUI(
                    lazyPagingItems = catsExplorerViewModel.getCatsPagingDataFlow().collectAsLazyPagingItems(),
                    fetchCatBreedDetail = { id ->
                        catsExplorerViewModel.fetchCatBreedDetail(id = id)
                    },
                    catDetailsState = catsExplorerViewModel.catBreedDetailFlow.collectAsStateWithLifecycle().value
                )
            }
        }
    }
}