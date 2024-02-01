package com.assignment.catexplorer.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.assignment.catexplorer.data.local.CatBreedEntity

@Composable
fun CatItem(
    cat: CatBreedEntity,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Async Image
            AsyncImage(
                model = cat.imageUrl,
                contentDescription = cat.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Text Content
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = cat.name.orEmpty(),
                    style = MaterialTheme.typography.h6,
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
                    maxLines = 3,
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
                imageUrl = null
            ),
            modifier = Modifier.fillMaxWidth()
        )
}