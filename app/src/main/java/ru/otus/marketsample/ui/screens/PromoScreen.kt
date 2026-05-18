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
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.PromoState
import ru.otus.marketsample.ui.widgets.LoadingProgress
import ru.otus.marketsample.ui.widgets.promo.PromoItem

@Composable
fun PromoScreen(
    promoListViewModel: PromoListViewModel,
    modifier: Modifier = Modifier,
    onError: (() -> Unit)? = null
) {
    val state by promoListViewModel.state.collectAsState()
    var isRefresh by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    when {
        state.isLoading -> LoadingProgress(modifier)
        state.hasError -> onError?.invoke()
        else -> PromoContent(
            modifier = modifier,
            promoListState = state.promoListState,
            isRefresh = isRefresh,
            onRefresh = {
                isRefresh = true
                promoListViewModel.refresh()
                coroutineScope.launch {
                    isRefresh = false
                }
            }
        )
    }
}

@Composable
fun PromoContent(
    promoListState: List<PromoState>,
    isRefresh: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefresh,
        onRefresh = onRefresh
    ) {
        LazyColumn {
            items(
                items = promoListState,
                key = { it.id }
            ) {
                PromoItem(promoState = it)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PromoContentPreview() {
    val listState = listOf(
        PromoState(
            id = "foo",
            name = "Name #1",
            description = "Bla bla bla #1",
            image = ""
        ),
        PromoState(
            id = "bar",
            name = "Name #2",
            description = "Bla bla bla #2",
            image = ""
        )
    )
    PromoContent(
        promoListState = listState,
        isRefresh = false,
        onRefresh = {}
    )
}