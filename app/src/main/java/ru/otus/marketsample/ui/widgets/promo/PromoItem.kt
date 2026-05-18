package ru.otus.marketsample.ui.widgets.promo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.otus.marketsample.promo.feature.PromoState
import ru.otus.marketsample.ui.common.White

@Composable
fun PromoItem(
    promoState: PromoState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .background(Color(0x99ff0000))
                .drawWithContent {
                    val gradientHeight = 120.dp.toPx()
                    drawContent()
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xAA000000),
                                Color(0x00000000)
                            ),
                            start = Offset(x = 0f, y = size.height),
                            end = Offset(x = 0f, y = size.height - gradientHeight),
                        ),
                    )
                },
            contentScale = ContentScale.Crop,
            model = promoState.image,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = promoState.name,
                fontSize = 24.sp,
                color = White
            )
            Text(
                text = promoState.description,
                fontSize = 14.sp,
                color = White
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PromoItemPreview() {
    PromoItem(
        promoState = PromoState(
            id = "foo-bar",
            name = "Name",
            description = "Bla bla bla",
            image = ""
        )
    )
}