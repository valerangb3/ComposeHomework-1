package ru.otus.marketsample.ui

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ru.otus.marketsample.ProductDetail
import ru.otus.marketsample.ProductList
import ru.otus.marketsample.Promo
import ru.otus.marketsample.R
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.marketsample.details.feature.DetailsViewModelFactory
import ru.otus.marketsample.details.feature.di.DaggerDetailsComponent
import ru.otus.marketsample.di.AppComponent
import ru.otus.marketsample.presentation.Inject
import ru.otus.marketsample.presentation.daggerViewModel
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.ProductListViewModelFactory
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.PromoListViewModelFactory
import ru.otus.marketsample.promo.feature.di.DaggerPromoComponent
import ru.otus.marketsample.ui.screens.DetailScreen
import ru.otus.marketsample.ui.screens.ProductsScreen
import ru.otus.marketsample.ui.screens.PromoScreen

@Composable
fun App(
    appComponent: AppComponent,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val shouldShowBottomBar = backStackEntry?.destination?.hasRoute<ProductDetail>() == false
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<ProductList>() == true,
                        onClick = {
                            navController.navigate(ProductList) {
                                popUpTo(navController.graph.findStartDestination().id) {saveState = true}
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(ru.otus.common.ui.R.drawable.ic_list),
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(R.string.title_products)) }
                    )
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<Promo>() == true,
                        onClick = {
                            navController.navigate(Promo) {
                                popUpTo(navController.graph.findStartDestination().id) {saveState = true}
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(ru.otus.common.ui.R.drawable.ic_discount),
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(R.string.title_promo)) }
                    )
                }
            }
        }
    ) { contentPadding ->
        AppNavHost(
            appComponent = appComponent,
            navController = navController,
            modifier = Modifier.padding(contentPadding)
        )
    }

}

@Composable
fun AppNavHost(
    appComponent: AppComponent,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ProductList,
        modifier = modifier
    ) {
        composable<ProductList> {
            val vmFactory = DaggerProductListComponent.factory()
                .create(appComponent)
                .productListViewModelFactory()
            ProductsListScreen(
                vmFactory = vmFactory,
                navController = navController
            )
        }
        composable<ProductDetail> { backStackEntry ->
            val productDetail: ProductDetail = backStackEntry.toRoute()
            val vmFactoryDetail = DaggerDetailsComponent.factory()
                .create(
                    appComponent,
                    productDetail.productId
                )
                .detailsViewModelFactory()
            ProductsDetailScreen(vmFactory = vmFactoryDetail)
        }
        composable<Promo> {
            val vmPromoFactory = DaggerPromoComponent.factory()
                .create(appComponent)
                .promoListViewModelFactory()
            PromoListScreen(
                vmFactory = vmPromoFactory
            )
        }
    }
}

@Composable
fun ProductsDetailScreen(
    vmFactory: DetailsViewModelFactory,
) {
    Inject(vmFactory) {
        val viewModel = daggerViewModel<DetailsViewModel>()
        val context = LocalContext.current
        DetailScreen(
            detailsViewModel = viewModel,
            onError = {
                Toast.makeText(
                    context,
                    "Error wile loading data",
                    Toast.LENGTH_SHORT
                ).show()

                viewModel.errorHasShown()
            }
        )
    }
}

@Composable
fun ProductsListScreen(
    vmFactory: ProductListViewModelFactory,
    navController: NavController
) {
    Inject(vmFactory) {
        val context = LocalContext.current
        val viewModel = daggerViewModel<ProductListViewModel>()
        ProductsScreen(
            productListViewModel = viewModel,
            onClick = { productId ->
                navController.navigate(ProductDetail(productId))
            },
            onError = {
                Toast.makeText(
                    context,
                    "Error wile loading data",
                    Toast.LENGTH_SHORT
                ).show()

                viewModel.errorHasShown()
            }
        )
    }
}

@Composable
fun PromoListScreen(
    vmFactory: PromoListViewModelFactory
) {
    Inject(vmFactory) {
        val context = LocalContext.current
        val viewModel = daggerViewModel<PromoListViewModel>()
        PromoScreen(
            promoListViewModel = viewModel,
            onError = {
                Toast.makeText(
                    context,
                    "Error wile loading data",
                    Toast.LENGTH_SHORT
                ).show()

                viewModel.errorHasShown()
            }
        )
    }
}