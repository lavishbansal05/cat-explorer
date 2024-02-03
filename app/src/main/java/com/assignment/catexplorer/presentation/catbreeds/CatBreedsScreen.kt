import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.assignment.catexplorer.data.local.CatBreedEntity
import com.assignment.catexplorer.presentation.catbreeds.CatItem
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import com.assignment.catexplorer.R


@Composable
fun CatsScreen(
    cats: LazyPagingItems<CatBreedEntity>,
    onItemClick: (catId: String) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = cats.loadState) {
        if (cats.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (cats.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.secondary)) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                )
            },
            backgroundColor = MaterialTheme.colors.surface,
            elevation = AppBarDefaults.TopAppBarElevation
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if (cats.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(
                        items = cats,
                        key = { index, item -> item.id + "$index" }) { _, item ->
                        if (item != null) {
                            CatItem(
                                cat = item,
                                modifier = Modifier.fillMaxWidth(),
                                onClick = onItemClick
                            )
                        }
                    }
                    item {
                        if (cats.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}




