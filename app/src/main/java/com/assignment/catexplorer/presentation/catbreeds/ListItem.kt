package com.assignment.catexplorer.presentation.catbreeds

import androidx.annotation.DrawableRes
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
import com.assignment.catexplorer.R
import com.assignment.catexplorer.presentation.ui.theme.CatExplorerTheme

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    id: String,
    name: String?,
    description: String?,
    imageUrl: String?,
    @DrawableRes placeholder: Int,
    onClick: (catId: String) -> Unit,
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .clickable { onClick(id) },
        elevation = 4.dp,
        shape = MaterialTheme.shapes.small,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl ?: placeholder ,
                placeholder = painterResource(id = placeholder),
                contentDescription = name,
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

                name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CatItemPreview() {
    CatExplorerTheme(darkTheme = true) {
        ListItem(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            placeholder = R.drawable.ic_cat_placeholder,
            id = "id",
            name = "Aegan",
            description = "Native to the greek island known as Cyclades i nthe Aegan Sea, these are natural cats..",
            imageUrl = "imageUrl"
        )
    }
}