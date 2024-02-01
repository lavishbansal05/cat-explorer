package com.assignment.catexplorer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.assignment.catexplorer.R

@Composable
fun ListItemsView(
    modifier: Modifier = Modifier,
    id: String,
    imageUrl: String,
    title: String,
    description: String,
    onItemClick: (itemId: String) -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(
                onClick = {
                    onItemClick(id)
                }
            ),
        shape = RoundedCornerShape(4.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (image, titleView, content) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .constrainAs(image) {
                        centerVerticallyTo(parent)
                        start.linkTo(parent.start)
                    }
                    .height(64.dp)
                    .width(64.dp)
                    .aspectRatio(1f),
                model = imageUrl,
                contentDescription = title,
                placeholder = painterResource(id = R.drawable.ic_cat_placeholder),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.constrainAs(titleView) {
                    top.linkTo(parent.top)
                    start.linkTo(image.end, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    width = Dimension.fillToConstraints
                },
                text = title,
                maxLines = 1,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1

            )
            Text(
                modifier = Modifier
                    .constrainAs(content) {
                        start.linkTo(image.end, margin = 8.dp)
                        end.linkTo(parent.end, margin = 8.dp)
                        top.linkTo(titleView.bottom)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    },
                text = description,
                maxLines = 2,
                style = MaterialTheme.typography.body1,
            )
        }

    }
}

//@Composable
//@Preview(name = "HomeItemsView Light")
//private fun ListItemsViewPreviewLight() {
//    AppTheme(darkTheme = false) {
//        ListItemsView(
//            item = MovieItem.mock(),
//            onItemClick = { }
//        )
//    }
//}
