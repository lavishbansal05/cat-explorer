package com.assignment.catexplorer.presentation.catbreeds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.assignment.catexplorer.data.local.CatBreedEntity
import com.assignment.catexplorer.R

@Composable
fun CatItem(
    cat: CatBreedEntity,
    modifier: Modifier = Modifier,
    onClick: (catId: String) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick(cat.id) },
        elevation = 4.dp,
        shape = MaterialTheme.shapes.small,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = cat.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_cat_placeholder),
                contentDescription = cat.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(164.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 12.dp, horizontal = 6.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = cat.name.orEmpty(),
                    style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = cat.description.orEmpty(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun CatItemPreview() {
    CatItem(
        cat = CatBreedEntity(
            id = "1",
            name = "Meow",
            description = "This is a meow cat",
            imageUrl = null,
            lifeSpan = "5-6 years",
            origin = "Burma",
            temperament = "Playful",
            childFriendly = 5,
            intelligence = 4,
            affectionLevel = 3
        ),
        modifier = Modifier.fillMaxWidth(),
        onClick = {}
    )
}