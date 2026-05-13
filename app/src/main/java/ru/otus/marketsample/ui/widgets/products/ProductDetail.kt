package ru.otus.marketsample.ui.widgets.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.otus.marketsample.details.feature.DetailsState
import ru.otus.marketsample.ui.common.Black
import ru.otus.marketsample.ui.common.Purple500
import ru.otus.marketsample.ui.common.White
import ru.otus.marketsample.ui.widgets.Discount

@Composable
fun ProductDetail(
    detailsState: DetailsState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .background(Color(0x99ff0000)),
                model = "",
                contentDescription = null,
            )
            Text(
                modifier = Modifier
                    .padding(),
                color = Black,
                fontSize = 24.sp,
                text = detailsState.name,
            )
            if (detailsState.hasDiscount) {
                Discount(
                    modifier = Modifier
                        .align(Alignment.End),
                    fontSize = 20.sp,
                    text = detailsState.discount
                )
            }
            Text(
                modifier = Modifier
                    .padding(14.dp)
                    .align(Alignment.End),
                text = detailsState.price,
                color = Purple500,
                fontSize = 18.sp,
            )
            Button(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.End),
                onClick = {},
                contentPadding = PaddingValues(14.dp)
            ) {
                Text(
                    text = "Add to cart".uppercase(),
                    color = White,
                    fontWeight = FontWeight.W700
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProductDetailPreview() {
    val state = DetailsState(
        id = "4ac-32q",
        name = "Product Name".uppercase(),
        image = "",
        price = "2000 руб",
        hasDiscount = true,
        discount = "-20%"
    )
    ProductDetail(
        detailsState = state
    )
}