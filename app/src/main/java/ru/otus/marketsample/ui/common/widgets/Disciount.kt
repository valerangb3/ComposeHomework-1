package ru.otus.marketsample.ui.common.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import ru.otus.marketsample.ui.common.Purple200
import ru.otus.marketsample.ui.common.Purple500
import ru.otus.marketsample.ui.common.White

@Composable
fun Discount(
    text: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.W400,
) {
    val discountShape = RoundedCornerShape(
        topStart = 40.dp,
        topEnd = 10.dp,
        bottomStart = 40.dp,
        bottomEnd = 40.dp
    )
    Text(
        modifier = modifier
            .clip(discountShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Purple200, Purple500),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                )
            )
            .border(
                width = 2.dp,
                color = White,
                shape = discountShape
            )
            .padding(horizontal = 10.dp, vertical = 4.dp),
        color = White,
        fontWeight = fontWeight,
        fontSize = fontSize,
        text = text,
    )
}