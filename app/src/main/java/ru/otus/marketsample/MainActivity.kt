package ru.otus.marketsample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.toRoute
import ru.otus.marketsample.databinding.ActivityMainBinding
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.marketsample.details.feature.DetailsViewModelFactory
import ru.otus.marketsample.details.feature.di.DaggerDetailsComponent
import ru.otus.marketsample.di.AppComponent
import ru.otus.marketsample.di.DaggerAppComponent
import ru.otus.marketsample.presentation.Inject
import ru.otus.marketsample.presentation.daggerViewModel
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.ProductListViewModelFactory
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import ru.otus.marketsample.ui.screens.DetailScreen
import ru.otus.marketsample.ui.screens.ProductsScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val appComponent = (this.applicationContext as MarketSampleApp).appComponent

        setContent {
            //FirstScreen(vmFactory)
            App(appComponent)
        }
        /*binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.container) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
}

@Composable
fun App(
    appComponent: AppComponent,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                NavigationBarItem(
                    selected = true,
                    onClick = {

                    },
                    icon = {

                    },
                    label = { Text("Foo") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {

                    },
                    icon = {

                    },
                    label = { Text("Foo") }
                )
            }
        }
    ) { contentPadding ->
        AppNavHost(
            appComponent = appComponent,
            navController = navController,
            modifier = Modifier.padding(contentPadding)
        )
    }

    //SecondScreen(vmFactoryDetail)
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
            FirstScreen(
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
            SecondDetailScreen(
                vmFactory = vmFactoryDetail,
                navController = navController
            )
        }
        composable<Promo> {  }
    }
}

@Composable
fun SecondDetailScreen(
    vmFactory: DetailsViewModelFactory,
    navController: NavController
) {
    Inject(vmFactory) {
        val context = LocalContext.current
        val viewModel = daggerViewModel<DetailsViewModel>()
        //val productDetail = navController.to
        DetailScreen(viewModel)
    }
}

@Composable
fun FirstScreen(
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