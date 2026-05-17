package ru.otus.marketsample.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.ProductState
import ru.otus.marketsample.ui.widgets.LoadingProgress
import ru.otus.marketsample.ui.widgets.products.ProductItem


@Composable
fun ProductsScreen(
    productListViewModel: ProductListViewModel,
    onClick: (productId: String) -> Unit,
    modifier: Modifier = Modifier,
    onError: (() -> Unit)? = null,
) {
    val state by productListViewModel.state.collectAsState()
    var isRefresh by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    when {
        state.isLoading -> LoadingProgress()
        state.hasError -> onError?.invoke()
        else -> ProductsContent(
            productListState = state.productListState,
            onClick = onClick,
            modifier = modifier,
            onRefresh = {
                isRefresh = true
                productListViewModel.refresh()
                coroutineScope.launch {
                    isRefresh = false
                }
            },
            isRefresh = isRefresh
        )
    }
}

@Composable
fun ProductsContent(
    productListState: List<ProductState>,
    onClick: (productId: String) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    isRefresh: Boolean,
) {
    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefresh,
        onRefresh = onRefresh
    ) {
        LazyColumn {
            items(
                items = productListState,
                key = { it.id }
            ) { productState ->
                ProductItem(
                    productsState = productState,
                    onClick = onClick
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProductsContentPreview() {
    val items = listOf(
        ProductState(
            id = "89344",
            name = "Product #1",
            image = "",
            price = "200 руб",
            hasDiscount = true,
            discount = "-20%"
        ),
        ProductState(
            id = "89345",
            name = "Product #2",
            image = "",
            price = "250 руб",
            hasDiscount = true,
            discount = "-10%"
        ),
        ProductState(
            id = "89346",
            name = "Product #3",
            image = "",
            price = "100 руб",
            hasDiscount = false,
            discount = ""
    )
    )
    ProductsContent(
        productListState = items,
        onClick = {},
        onRefresh = {},
        isRefresh = false
    )
}