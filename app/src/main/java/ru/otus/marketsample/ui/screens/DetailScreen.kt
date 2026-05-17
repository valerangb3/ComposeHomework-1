package ru.otus.marketsample.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.otus.marketsample.details.feature.DetailsState
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.marketsample.ui.widgets.products.ProductDetail

@Composable
fun DetailScreen(
    detailsViewModel: DetailsViewModel,
    modifier: Modifier = Modifier
) {
    val state by detailsViewModel.state.collectAsState()

    when {
        state.isLoading -> {}
        state.hasError -> {}
        else -> ProductDetail(
            modifier = modifier,
            detailsState = state.detailsState
        )
    }
}

@Composable
fun DetailScreenPreview() {
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