package ru.otus.marketsample.ui.widgets.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.otus.marketsample.R
import ru.otus.marketsample.products.feature.ProductState
import ru.otus.marketsample.ui.common.Black
import ru.otus.marketsample.ui.common.Purple500
import ru.otus.marketsample.ui.widgets.Discount

@Composable
fun ProductItem(
    productsState: ProductState,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(modifier = Modifier.height(130.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0x99ff0000)),
                    model = productsState.image,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                )
                if (productsState.hasDiscount) {
                    Discount(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset((-8).dp, 8.dp),
                        text = productsState.discount,
                        fontWeight = FontWeight.W700,
                        fontSize = 14.sp
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    color = Black,
                    overflow = TextOverflow.Ellipsis,
                    text = productsState.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    maxLines = 2,
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFFFF3E0))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .align(Alignment.BottomEnd),
                        text = productsState.price,
                        textAlign = TextAlign.End,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        color = Purple500
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProductItemPreview() {
    val productState = ProductState(
        id = "89344",
        name = "Product #1",
        image = "",
        price = "200 руб",
        hasDiscount = true,
        discount = "-20%"
    )
    ProductItem(productState)
}