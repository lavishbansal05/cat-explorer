package com.assignment.catexplorer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.assignment.catexplorer.MyApplication
import com.assignment.catexplorer.presentation.di.vmfactory.CatExplorerViewModelFactory
import com.assignment.catexplorer.presentation.ui.theme.CatExplorerTheme
import javax.inject.Inject

class CatsExplorerActivity : ComponentActivity() {

    @Inject
    lateinit var catExplorerViewModelFactory: CatExplorerViewModelFactory

    private lateinit var catsExplorerViewModel: CatsExplorerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as MyApplication).appComponent.inject(this)
        catsExplorerViewModel =
            ViewModelProvider(this, catExplorerViewModelFactory)[CatsExplorerViewModel::class.java]

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