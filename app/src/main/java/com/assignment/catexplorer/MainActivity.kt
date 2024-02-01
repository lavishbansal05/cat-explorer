package com.assignment.catexplorer

import CatsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.paging.compose.collectAsLazyPagingItems
import com.assignment.catexplorer.di.AppModule
import com.assignment.catexplorer.di.CatBreedsViewModelFactory
import com.assignment.catexplorer.di.DaggerAppComponent
import com.assignment.catexplorer.viewmodel.CatBreedsViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: CatBreedsViewModelFactory

    private lateinit var viewModel: CatBreedsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.builder().appModule(AppModule(context = this.applicationContext)).build()
        appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[CatBreedsViewModel::class.java]

        setContent {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val lazyCatItems = viewModel.getCatsFlow().collectAsLazyPagingItems()
                    CatsScreen(cats = lazyCatItems)
                }

        }
    }
}