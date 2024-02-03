package com.assignment.catexplorer.presentation.catdetails

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.assignment.catexplorer.R
import com.assignment.catexplorer.data.local.CatBreedEntity


@Composable
fun CatDetailsScreen(
    fetchCatBreedDetail: (id: String) -> Unit,
    catBreedId: String?,
    catDetailsState: CatBreedDetailState,
    onBack: () -> Unit,
) {
    if (catBreedId == null) {
        Toast.makeText(
            LocalContext.current,
            stringResource(id = R.string.cat_detail_error_msg),
            Toast.LENGTH_LONG
        ).show()
    } else {
        LaunchedEffect(Unit) {
            fetchCatBreedDetail(catBreedId)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (catDetailsState) {
                is CatBreedDetailState.Initial -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is CatBreedDetailState.Loaded -> {
                    CatDetailsScreenUI(
                        modifier = Modifier,
                        onBack = onBack,
                        catBreedEntity = catDetailsState.catBreedEntity
                    )
                }

                is CatBreedDetailState.Error -> {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(id = R.string.cat_detail_error_msg),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

@Composable
fun CatDetailsScreenUI(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    catBreedEntity: CatBreedEntity,
) {
    Column(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
        TopAppBar(
            title = {
                Text(
                    text = catBreedEntity.name.orEmpty(),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.surface,
            elevation = AppBarDefaults.TopAppBarElevation
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)

        ) {
            // Cat Name
            Spacer(modifier = Modifier.height(16.dp))

            // Header Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                // Cat Image
                AsyncImage(
                    model = catBreedEntity.imageUrl,
                    contentDescription = catBreedEntity.name,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_cat_placeholder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Attributes Table
            CatDetailCell(
                stringResource(id = R.string.lifespan),
                catBreedEntity.lifeSpan.orEmpty()
            )
            CatDetailCell(
                stringResource(id = R.string.origin),
                catBreedEntity.origin.orEmpty()
            )
            CatDetailCell(
                stringResource(id = R.string.temperament),
                catBreedEntity.temperament.orEmpty()
            )
            CatDetailCell(
                stringResource(id = R.string.child_friendly),
                catBreedEntity.childFriendly.toString()
            )
            CatDetailCell(
                stringResource(id = R.string.intelligence),
                catBreedEntity.intelligence.toString()
            )
            CatDetailCell(
                stringResource(id = R.string.affection_level),
                catBreedEntity.affectionLevel.toString()
            )


            Spacer(modifier = Modifier.height(16.dp))

            // Description Section
            Text(
                text = catBreedEntity.description.orEmpty(),
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.secondary,
                    lineHeight = 28.sp
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

    }

}


@Composable
fun CatDetailCell(key: String, value: String) {
    Text(
        text = "$key: $value",
        style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.W700
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    )
}


@Preview
@Composable
fun PreviewCatDetailCell() {
    CatDetailCell(key = "Origin", value = "Burma")
}
