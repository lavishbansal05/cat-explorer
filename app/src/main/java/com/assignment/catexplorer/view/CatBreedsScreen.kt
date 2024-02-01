import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.assignment.catexplorer.data.local.CatBreedEntity
import com.assignment.catexplorer.view.CatItem
import androidx.paging.compose.items


@Composable
fun CatsScreen(
    cats: LazyPagingItems<CatBreedEntity>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = cats.loadState) {
        if(cats.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (cats.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if(cats.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(cats) { cat ->
                    if(cat != null) {
                        CatItem(
                            cat = cat,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                item {
                    if(cats.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}




