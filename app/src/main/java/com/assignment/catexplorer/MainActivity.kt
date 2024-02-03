package com.assignment.catexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.assignment.catexplorer.di.AppModule
import com.assignment.catexplorer.di.CatBreedsViewModelFactory
import com.assignment.catexplorer.di.DaggerAppComponent
import com.assignment.catexplorer.presentation.CatBreedsViewModel
import com.assignment.catexplorer.presentation.CatsExplorerUI
import com.assignment.catexplorer.ui.theme.CatExplorerTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var catBreedsViewModelFactory: CatBreedsViewModelFactory

    private lateinit var catBreedsViewModel: CatBreedsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent =
            DaggerAppComponent.builder().appModule(AppModule(context = this.applicationContext))
                .build()
        appComponent.inject(this)
        catBreedsViewModel =
            ViewModelProvider(this, catBreedsViewModelFactory)[CatBreedsViewModel::class.java]

        setContent {
            CatExplorerTheme {
                CatsExplorerUI(
                    lazyPagingItems = catBreedsViewModel.catsPagingDataFlow.collectAsLazyPagingItems(),
                    fetchCatBreedDetail = { id ->
                        catBreedsViewModel.fetchCatBreedDetail(id = id)
                    },
                    catDetailsState = catBreedsViewModel.catBreedDetailFlow.collectAsStateWithLifecycle().value
                )
            }
        }
    }
}